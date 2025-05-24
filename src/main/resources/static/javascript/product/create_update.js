document.addEventListener("DOMContentLoaded", () => {

    const createProductBtn = document.getElementById("createProductButton")
    if(createProductBtn) {
        createProductBtn.addEventListener('click', () => {
            createProduct()
        })
    }

    const updateProductBtn = document.getElementById("updateProductButton")
    if(updateProductBtn) {
        updateProductBtn.addEventListener('click', () =>{
            updateProduct()
        })
    }

    const deleteProductBtn = document.getElementById("deleteProductButton")
    if(deleteProductBtn) {
        deleteProductBtn.addEventListener('click', () => {
            if(confirm("Bạn có chắc muốn xóa sản phẩm này?")){
                deleteProduct()
            }
        })
    }


    document.getElementById("changeThumbnailButton").addEventListener('click', () => {
        document.getElementById("thumbnailInput").click()
    })
    document.getElementById("thumbnailInput").addEventListener("change", function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                const thumbnailPreview = document.getElementById("thumbnailPreview");
                thumbnailPreview.src = e.target.result;
                thumbnailPreview.onload = () => URL.revokeObjectURL(thumbnailPreview.src);
            };
            reader.readAsDataURL(file);
        }
    });
})

async function createProduct() {
    const formData = new FormData();

    const categoryId = document.getElementById("category").value;
    const name = document.getElementById("name").value.trim();
    const description = document.getElementById("description").value.trim();
    const price = document.getElementById("price").value;
    const brand = document.getElementById("brand").value.trim();
    const color = document.getElementById("color").value.trim();

    if (name.length < 1) {
        showError("Tên sản phẩm không được trống");
        return;
    }
    else if (price < 0) {
        showError("Giá sản phẩm cần từ 0 trở lên");
        return;
    }

    formData.append("category.id", categoryId);
    formData.append("name", name);
    formData.append("description", description);
    formData.append("price", price);
    formData.append("brand", brand);
    formData.append("color", color);

    const thumbnail = document.getElementById('thumbnailInput').files[0];
    if (thumbnail) {
        if (!validateImage(thumbnail)) {
            return;
        }
        const compressedBlob = await compressToWebp(thumbnail, 0.7);
        formData.append("image", compressedBlob, "product.webp");
    }

    axios.post('/api/product/create', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
        .then(response => {
            if (response.status === 201) {
                showSuccess(response.data);
                setTimeout(() => {
                    window.location.href = "/";
                }, 3000);
            } else {
                console.log(response);
                alert("Lỗi không xác định:\n" + JSON.stringify(response.data));
            }
        })
        .catch(error => {
            showError(error.response.data);
        });
}


async function updateProduct(){
    const formData = new FormData()

    const categoryId = document.getElementById("category").value;
    const name = document.getElementById("name").value.trim();
    const description = document.getElementById("description").value.trim();
    const price = document.getElementById("price").value;
    const brand = document.getElementById("brand").value.trim();
    const color = document.getElementById("color").value.trim();
    const isActive = document.getElementById("isActive").checked;
    formData.append("category.id", categoryId);
    formData.append("name", name);
    formData.append("description", description);
    formData.append("price", price);
    formData.append("brand", brand);
    formData.append("color", color);
    formData.append("active", isActive);
    if(!checkProductValues()){
        return
    }
    const thumbnail = document.getElementById('thumbnailInput').files[0];
    if (thumbnail) {
        if (!validateImage(thumbnail)) {
            return;
        }
        const compressedBlob = await compressToWebp(thumbnail, 0.7);
        formData.append("image", compressedBlob, "product.webp");
    }
    formData.append("id",document.getElementById("id").value)

    axios.put('/api/product/update', formData, {
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

function deleteProduct() {
    const id = document.getElementById("id").value
    axios.delete(`/api/Product/delete/${id}`)
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

function checkProductValues(name, categoryId, description, price, brand, color) {
    if (categoryId === "") {
        showError("Vui lòng chọn danh mục trống")
        return false;
    }

    if (name === "") {
        showError("Vui lòng nhập tên sản phẩm")
        return false;
    }

    if (description === "") {
        showError("Vui lòng nhập mô tả sản phẩm")
        return false;
    }
    if (price < 0) {
        showError("Giá sản phẩm cần lớn hơn hoặc bằng 0")
        return false;
    }
    if (brand === "") {
        showError("Vui lòng nhập tên thương hiệu")
        return false;
    }
    if (color === "") {
        showError("Nhập màu sắc sản phẩm")
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


