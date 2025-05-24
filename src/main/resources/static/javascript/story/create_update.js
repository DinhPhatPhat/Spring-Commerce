document.addEventListener("DOMContentLoaded", () => {

    const createStoryBtn = document.getElementById("createStoryButton")
    if(createStoryBtn) {
        createStoryBtn.addEventListener('click', () => {
            createStory()
        })
    }

    const updateStoryBtn = document.getElementById("updateStoryButton")
    if(updateStoryBtn) {
        updateStoryBtn.addEventListener('click', () =>{
            updateStory()
        })
    }

    const deleteStoryBtn = document.getElementById("deleteStoryButton")
    if(deleteStoryBtn) {
        deleteStoryBtn.addEventListener('click', () => {
            if(confirm("Bạn có chắc muốn xóa câu chuyện?")){
                deleteStory()
            }
        })
    }

    tinymce.init({
        selector: 'textarea#content',
        menubar: 'file edit view',
        height: 600,
        promotion: false,
        setup: function (editor) {
            if(createStoryBtn) {
                createStoryBtn.addEventListener("click", function () {
                    editor.save()
                })
            }
            if(updateStoryBtn) {
                updateStoryBtn.addEventListener("click", function () {
                    editor.save()
                })
            }
        }
    })
})

async function createStory() {

    const formData = new FormData()
    tinymce.get('content').save()

    const title = document.getElementById("title").value
    const content = document.getElementById('content').value

    if (!checkTitleContent(title, content)) {
        return
    }
    formData.append("title", title)
    formData.append("content", content)

    const storyImage = document.getElementById('storyImage').files[0]
    if (storyImage != null) {
        if (!validateImage(storyImage)) {
            return
        }
        const compressedBlob = await compressToWebp(storyImage, 0.7)
        formData.append("image", compressedBlob, "compressed.webp")
    }
    axios.post('/api/story/create', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
        .then(response => {
            if (response.status === 201) {
                showSuccess(response.data)
                setTimeout(() => {
                    window.location.href = "/user"
                }, 3000)

            } else {
                console.log(response);
                alert("Lỗi không xác định:\n" + JSON.stringify(response.data))
            }
        })
        .catch(error => {
            showError(error.response.data)
        })
}

async function updateStory(){
    const formData = new FormData()
    tinymce.get('content').save()

    const title = document.getElementById("title").value
    const content = document.getElementById("content").value

    formData.append("title", title)
    formData.append("content", content)

    if(!checkTitleContent(title,content)){
        return
    }
    formData.append("id",document.getElementById("id").value)

    const storyImage = document.getElementById('storyImage').files[0]
    if (storyImage != null) {
        if(!validateImage(storyImage)){
            return
        }
        const compressedBlob = await compressToWebp(storyImage, 0.7)
        formData.append("image", compressedBlob, "compressed.webp")
    }

    axios.put('/api/story/update', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
        .then(response => {
            if (response.status === 200) {
                showSuccess(response.data, 3000);
                setTimeout(() => {
                    location.reload();
                }, 3000);
            } else {
                console.log(response);
                showError("Lỗi không xác định")
            }
        })
        .catch(error => {
            showError(error.response.data)
        });
}

function deleteStory() {
    const id = document.getElementById("id").value
    axios.delete(`/api/story/delete/${id}`)
        .then(response => {
            showSuccess(response.data)
            setTimeout(() => {
                window.location.href = "/user"
            }, 3000)
        })
        .catch(error => {
            showError(error.response.data)
        })
}

function checkTitleContent(title, content) {
    if (title === "") {
        showError("Tiêu đề không được trống")
        return false;
    }

    if (content === "") {
        showError("Nội dung không được trống")
        return false;
    }

    if (content.length < 1000) {
        showError("Câu chuyện quá ngắn (cần trên 1000 ký tự)")
        return false;
    }
    return true;
}

function validateImage(file) {
    const maxFileSize = 5 * 1024 * 1024
    if (file.size > maxFileSize) {
        showError('Ảnh phải nhỏ hơn 5MB')
        return false
    }
    return true
}

function compressToWebp(file, quality = 0.7, maxWidth = 1000) {
    return new Promise((resolve) => {
        const reader = new FileReader()
        reader.onload = function (event) {
            const img = new Image()
            img.onload = function () {
                const canvas = document.createElement('canvas')
                const ctx = canvas.getContext('2d')

                let width = img.width
                let height = img.height

                if (width > maxWidth) {
                    height = height * (maxWidth / width)
                    width = maxWidth
                }

                canvas.width = width
                canvas.height = height
                ctx.drawImage(img, 0, 0, width, height)

                canvas.toBlob((blob) => {
                    resolve(blob)
                }, 'image/webp', quality)
            }
            img.src = event.target.result
        }
        reader.readAsDataURL(file)
    })
}
