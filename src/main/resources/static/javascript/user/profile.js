document.addEventListener("DOMContentLoaded", () => {
    document.getElementById('logoutButton').addEventListener('click', () => {
        logout()
    })
})

function logout (){
    axios.post('/api/user/logout')
        .then(response => {
            window.location.href = "/"
            alert(response.data)
        })
}