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
                alert('Chia sẽ bài viết thành công')
                window.location.href = "/user"
            } else {
                console.log(response); // In ra để kiểm tra lỗi chi tiết
                alert("Lỗi không xác định:\n" + JSON.stringify(response.data));
            }
        })
        .catch(error => {
            if (error.response) {
                console.error("Chi tiết lỗi từ server:", error.response);
                alert("Lỗi từ server: " + error.response.data.message || error.response.statusText);
            } else {
                console.error("Lỗi không có phản hồi từ server:", error);
                alert("Lỗi không kết nối được đến server");
            }
        });
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
                alert(response.data)
                location.reload()
            } else {
                console.log(response);
                alert("Lỗi không xác định:\n" + JSON.stringify(response.data));
            }
        })
        .catch(error => {
            if (error.response) {
                console.error("Chi tiết lỗi từ server:", error.response);
                alert("Lỗi từ server: " + error.response.data.message || error.response.statusText);
            } else {
                console.error("Lỗi không có phản hồi từ server:", error);
                alert("Lỗi không kết nối được đến server");
            }
        });


}