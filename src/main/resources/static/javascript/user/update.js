document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("updateUserButton").addEventListener('click', () => {
        updateUser()
    })

    document.getElementById("changeAvatarButton").addEventListener('click', () => {
        document.getElementById("avatarInput").click()
    })

    document.getElementById("avatarInput").addEventListener("change", function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                const avatarPreview = document.getElementById("avatarPreview");
                avatarPreview.src = e.target.result;
                avatarPreview.onload = () => URL.revokeObjectURL(avatarPreview.src);
            };
            reader.readAsDataURL(file);
        }
    });
})


function updateUser() {
    const formData = new FormData()

    formData.append("name", document.getElementById("name").value)
    formData.append("phoneNumber", document.getElementById("phoneNumber").value)
    formData.append("dateOfBirth",document.getElementById("dateOfBirth").value)
    formData.append("bio",document.getElementById("bio").value)

    const userImage = document.getElementById("avatarInput").files[0]

    if(userImage) {
        formData.append("image", userImage)
    }

    axios.put("api/user/update", formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
        .then(response => {
            if (response.status === 200) {
                showSuccess(response.data, 3000);
                document.getElementById("updateUserButton").disabled = true
                setTimeout( () => {
                    document.getElementById("updateUserButton").disabled = false
                }, 10000)
            } else {
                console.log(response);
                showError("Lỗi không xác định")
            }
        })
        .catch(error => {
            showError(error.response.data)
        });
}