document.addEventListener("DOMContentLoaded", () => {
    const registerBtn = document.getElementById('registerButton')
    if (registerBtn) {
        registerBtn.addEventListener('click', () => {
            submitRegisterForm()
        })
    }

    const changePasswordBtn = document.getElementById('changePasswordButton')
    if (changePasswordBtn) {
        changePasswordBtn.addEventListener('click', () => {
            submitChangePasswordForm()
        })
    }

    const forgotPasswordBtn = document.getElementById('forgotPasswordButton')
    if (forgotPasswordBtn) {
        forgotPasswordBtn.addEventListener('click', () => {
            submitForgotPasswordForm()
        })
    }

    const loginBtn = document.getElementById('loginButton')
    if (loginBtn) {
        loginBtn.addEventListener('click', () => {
            submitLoginForm()
        })
    }
})

function submitRegisterForm() {
    const formData = new FormData;

    const name = document.getElementById('name')
    const phoneNumber = document.getElementById('phoneNumber')
    const dateOfBirth = document.getElementById('dateOfBirth')
    const registerBtn = document.getElementById('registerButton')

    registerBtn.disabled = true

    formData.append('name',document.getElementById('name').value)
    formData.append('phoneNumber',document.getElementById('phoneNumber').value)
    formData.append('dateOfBirth',document.getElementById('dateOfBirth').value)
    formData.append('bio',document.getElementById('bio').value)
    formData.append('email',document.getElementById('email').value)
    formData.append('password',document.getElementById('password').value)

    axios.post('/api/user/register', formData)
        .then(response => {
            if (response.status === 201) {
                registerBtn.disabled = false
                alert(`Tạo tài khoản thành công, vui lòng kiểm tra email ${response.data.email} để kích hoạt tài khoản`)
            } else {
                alert("Lỗi không xác định:\n" + response.data.join("\n"));
                registerBtn.disabled = false
            }
        })
        .catch(error => {
            registerBtn.disabled = false
            if(error.response.status === 400) {
                const errors = error.response.data
                if (Array.isArray(errors)) {
                    alert("Dữ liệu không hợp lệ:\n" + errors.join("\n"));
                } else {
                    alert("Lỗi: " + errors);
                }
            }
            else if (error.response.status === 409) {
                alert("Email đã tồn tại, vui lòng đăng nhập")
            }
            else if (error.response.status === 500) {
                alert("Lỗi server")
            }
            else {
                alert('Error: ' + error.message)
            }
        })
}

function submitChangePasswordForm(){
    const email= document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const password2 = document.getElementById('password2').value;

    const changePasswordBtn = document.getElementById('changePasswordButton');
    changePasswordBtn.disabled = true
    axios.post('/api/user/change-password', {
        email: email,
        password: password,
        password2: password2
    })
        .then(response => {
            changePasswordBtn.innerText = "Đổi mật khẩu thành công"
            alert(response.data)
        })
        .catch(error => {
            changePasswordBtn.disabled = false
            alert(error.response.data)
        })
}

function submitForgotPasswordForm() {
    const email = document.getElementById('email').value;
    const forgotPasswordBtn = document.getElementById('forgotPasswordButton');

    //Disable the button for 30s

    forgotPasswordBtn.disabled = true
    forgotPasswordBtn.innerText = "Vui lòng chờ 30 giây"

    axios.post('/api/user/forgot-password', null, {
        params: { email: email }
    })
        .then(response => {
            alert(response.data)
        })
        .catch(error => {
            alert(error.response.data)
        })
        .finally( () => {
            setTimeout( () => {
                forgotPasswordBtn.disabled = false
                forgotPasswordBtn.innerText = "Gửi link đổi mật khẩu"
            }, 30000)
        })
}

function submitLoginForm() {
    const email = document.getElementById('email').value
    const password = document.getElementById('password').value

    axios.post('/api/user/login', {
        email: email,
        password: password
    })
        .then(response => {
            window.location.href = "/user"
            alert(response.data)
        })
        .catch(error => {
            alert(error.response.data)
            password.value = ""
        })
}