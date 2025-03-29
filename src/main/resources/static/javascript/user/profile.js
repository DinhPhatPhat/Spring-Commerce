document.addEventListener("DOMContentLoaded", () => {

    $("#storyTable").DataTable({
        language: {
            search: "Tìm kiếm:",
            lengthMenu: "Hiển thị _MENU_ dòng",
            info: "Hiển thị _START_ - _END_ trên tổng _TOTAL_ dòng",
            infoEmpty: "",
            emptyTable: "Bạn chưa kể câu chuyện nào",
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
})

function logout (){
    axios.post('/api/user/logout')
        .then(response => {
            window.location.href = "/"
        })
}