document.addEventListener("DOMContentLoaded", () => {

    const createCategoryBtn = document.getElementById("createCategoryButton")
    if(createCategoryBtn) {
        createCategoryBtn.addEventListener('click', () => {
            createCategory()
        })
    }

    const updateCategoryBtn = document.getElementById("updateCategoryButton")
    if(updateCategoryBtn) {
        updateCategoryBtn.addEventListener('click', () =>{
            updateCategory()
        })
    }

    const deleteCategoryBtn = document.getElementById("deleteCategoryButton")
    if(deleteCategoryBtn) {
        deleteCategoryBtn.addEventListener('click', () => {
            if(confirm("Bạn có chắc muốn xóa câu chuyện?")){
                deleteCategory()
            }
        })
    }

    const productTable = document.getElementById("productTable")
    if(productTable){
        $("#productTable").DataTable({
            language: {
                search: "Tìm kiếm:",
                lengthMenu: "Hiển thị _MENU_ dòng",
                info: "Hiển thị _START_ - _END_ trên tổng _TOTAL_ dòng",
                infoEmpty: "",
                emptyTable: "Danh mục chưa có sản phẩm nào",
                zeroRecords: "",
                paginate: {
                    first: "|<",
                    last: ">|",
                    next: ">",
                    previous: "<"
                }
            }
        });
    }
})

async function createCategory() {
    const name = document.getElementById('name').value
    if (!checkName(name)) {
        return
    }
    axios.post('/api/category/create', {name: name})
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

async function updateCategory(){
    const name = document.getElementById("name").value
    const id = document.getElementById("id").value

    if(!checkName(name)){
        return
    }
    axios.put('/api/category/update', {id: id, name: name} )
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

function deleteCategory() {
    const id = document.getElementById("id").value
    axios.delete(`/api/category/delete/${id}`)
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

function checkName(name) {
    if (name === "") {
        showError("Tên không được trống")
        return false
    }
    return true
}
