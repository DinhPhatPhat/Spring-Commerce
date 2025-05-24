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


async function updateUser() {
    const formData = new FormData()
    const name = document.getElementById("name").value
    const phoneNumber = document.getElementById("phoneNumber").value
    const dateOfBirth = document.getElementById("dateOfBirth").value
    const bio = document.getElementById("bio").value

    if(!checkNamePhoneNumber(name, phoneNumber)){
        return
    }
    formData.append("name", name)
    formData.append("phoneNumber", phoneNumber)
    formData.append("dateOfBirth", dateOfBirth)
    formData.append("bio", bio)

    const userImage = document.getElementById("avatarInput").files[0]

    if(userImage) {
        if(!validateImage(userImage)){
            return
        }
        const compressedBlob = await compressToWebp(userImage, 0.7)
        formData.append("image", compressedBlob, "compressed.webp")
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

function checkNamePhoneNumber(name, phoneNumber) {
    if (name.trim() === '') {
        showError('Tên không được bỏ trống')
        return false
    }

    const phoneRegex = /^0[1-9][0-9]{8}$/
    if (!phoneRegex.test(phoneNumber)) {
        showError('Số điện thoại không hợp lệ')
        return false
    }
    return true
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

