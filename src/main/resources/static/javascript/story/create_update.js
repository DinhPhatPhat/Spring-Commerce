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

function createStory(){

    const formData = new FormData()
    const createStoryBtn = document.getElementById("createStoryButton")

    formData.append("title",document.getElementById("title").value)
    formData.append("content", document.getElementById('content').value)
    const storyImage = document.getElementById('storyImage').files[0]
    if (storyImage != null) {
        formData.append('image', storyImage);
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

function updateStory(){
    const formData = new FormData()

    formData.append("id",document.getElementById("id").value)
    formData.append("title", document.getElementById("title").value)
    formData.append("content", document.getElementById("content").value)

    const storyImage = document.getElementById('storyImage').files[0]
    if (storyImage != null) {
        formData.append('image', storyImage);
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
