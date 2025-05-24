document.addEventListener("DOMContentLoaded", async () => {
    try {
        const response = await axios.post('/api/category/findAll');

        if (response.status === 200) {
            const categories = response.data;
            const dropdownList = document.querySelector('#navmenu .dropdown > ul');

            dropdownList.innerHTML = "";

            categories.forEach(category => {
                const li = document.createElement("li");
                const a = document.createElement("a");
                a.href = `/category/${category.name}`;
                a.textContent = category.name;
                li.appendChild(a);
                dropdownList.appendChild(li);
            });
        } else {
            console.log(response);
            alert("Lỗi không xác định:\n" + JSON.stringify(response.data));
        }
    } catch (error) {
        console.error(error);
        alert("Lỗi khi tải danh mục: " + (error.response?.data || error.message));
    }
});