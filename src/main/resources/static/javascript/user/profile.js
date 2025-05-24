document.addEventListener("DOMContentLoaded", () => {

    $("#categoryTable").DataTable({
        language: {
            search: "Tìm kiếm:",
            lengthMenu: "Hiển thị _MENU_ dòng",
            info: "Hiển thị _START_ - _END_ trên tổng _TOTAL_ dòng",
            infoEmpty: "",
            emptyTable: "Chưa có danh mục sản phẩm nào",
            zeroRecords: "",
            paginate: {
                first: "|<",
                last: ">|",
                next: ">",
                previous: "<"
            }
        }
    });

    $("#orderTable").DataTable({
        language: {
            search: "Tìm kiếm:",
            lengthMenu: "Hiển thị _MENU_ dòng",
            info: "Hiển thị _START_ - _END_ trên tổng _TOTAL_ dòng",
            infoEmpty: "",
            emptyTable: "Chưa có đơn hàng nào",
            zeroRecords: "",
            paginate: {
                first: "|<",
                last: ">|",
                next: ">",
                previous: "<"
            }
        }
    });


    document.getElementById('logoutButton').addEventListener('click', () => {
        logout()
    })

    const selects = document.querySelectorAll('.order-status-select');

    selects.forEach(select => {
        select.addEventListener('change', function () {
            const orderId = this.getAttribute('data-id');
            const status = this.value;

            axios.post('/api/order/updateStatus', {
                orderId: orderId,
                status: status
            })
                .then(response => {
                    showSuccess("Cập nhật thành công", 3000);
                })
                .catch(error => {
                    showError("Cập nhật thất bại", 3000);
                    console.error(error);
                });
        });
    });
})

function logout (){
    axios.post('/api/user/logout')
        .then(response => {
            if(response.status === 200) {
                window.location.href = "/"
            }
        })
}