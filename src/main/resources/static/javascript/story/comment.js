document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("commentButton").addEventListener('click', () => {
        comment()
    })
    document.querySelectorAll('.deleteCommentButton').forEach(item => {
        item.addEventListener('click', function(event) {

            const commentId = this.getAttribute('data-id');

            deleteComment(commentId)


        })
    })
})

function comment() {
    const meta = window.location.pathname.split("/").pop(); // get meta from URL
    const content = document.getElementById("content").value.trim()

    if(content === "" ) {
        showError("Bạn nghĩ gì về câu chuyện?")
    }
    else {
        const formData = new FormData()

        formData.append("content", content)
        formData.append("meta", meta)

        axios.post("/api/comment/add", formData)
            .then(response => {
                if (response.status === 201) {
                    showSuccess("Đã thêm bình luận", 3000);
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

}

function deleteComment(commentId) {
        if(commentId) {
            axios.delete("/api/comment/delete", {
                params: { id: commentId }
            })
                .then(response => {
                    if (response.status === 200) {
                        showSuccess("Đã xóa bình luận")
                        const commentId = response.data.id;

                        const commentElement = document.getElementById('comment-' + commentId);
                        if (commentElement) {
                            commentElement.remove();
                        }

                    } else {
                        console.log(response);
                        alert("Lỗi không xác định:\n" + JSON.stringify(response.data))
                    }
                })
                .catch(error => {
                    showError(error.response.data)
                })
        }
}