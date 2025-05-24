document.addEventListener("DOMContentLoaded", function () {
    const storiesContainer = document.querySelector(".row.gy-5");
    const paginationContainer = document.querySelector(".pagination");

    const categorySelect = document.getElementById("category-select");
    const nameInput = document.getElementById("name-input");
    const brandInput = document.getElementById("brand-input");
    const colorInput = document.getElementById("color-input");
    const searchButton = document.getElementById("search-button");

    let currentPage = 1;

    // Load danh mục để chèn vào select
    axios.post("/api/category/findAll")
        .then(res => {
            res.data.forEach(c => {
                const option = document.createElement("option");
                option.value = c.id;
                option.textContent = c.name;
                categorySelect.appendChild(option);
            });
        });

    function fetchProducts(page = 1) {
        const params = {
            page,
            categoryId: categorySelect.value || null,
            name: nameInput.value.trim() || null,
            brand: brandInput.value.trim() || null,
            color: colorInput.value.trim() || null
        };

        axios.get("/api/product", { params })
            .then(response => {
                const { products, totalPages } = response.data;
                renderProducts(products);
                renderPagination(totalPages, page);
            })
            .catch(error => console.error("Lỗi tải dữ liệu:", error));
    }

    function renderProducts(products) {
        storiesContainer.innerHTML = products.map(p => `
            <div class="col-lg-4 col-md-6">
                <article>
                    <div class="post-img">
                        <a href="/product/${p.id}">
                            <img src="${p.imagePath || '/image/product/default.png'}" alt="" class="img-fluid thumbnail">
                        </a>
                    </div>
                    <div class="meta-top">
                        <ul>
                            <li><i></i> ${p.brand || "Không rõ"}</li>
                            <br>
                            <li><i class="bi bi-dot"></i> ${p.color || "Không rõ"}</li>
                            <br>
                            <li><i class="bi bi-dot"></i> ${p.price|| "Không rõ"} VNĐ</li>
                        </ul>
                    </div>
                    <h2 class="title">
                        <a href="/product/${p.id}">${p.name}</a>
                    </h2>
                </article>
            </div>
        `).join("");
    }

    function renderPagination(totalPages, currentPage) {
        paginationContainer.innerHTML = "";
        const maxVisiblePages = 5;

        const addPage = (page) => {
            const li = document.createElement("li");
            li.className = "page-item";
            const a = document.createElement("a");
            a.href = "#";
            a.textContent = page;
            a.className = "page-link" + (page === currentPage ? " active" : "");
            a.addEventListener("click", (e) => {
                e.preventDefault();
                if (currentPage !== page) {
                    currentPage = page;
                    fetchProducts(currentPage);
                }
            });
            li.appendChild(a);
            paginationContainer.appendChild(li);
        };

        if (totalPages > 1) addPage(1);

        let startPage = Math.max(2, currentPage - Math.floor(maxVisiblePages / 2));
        let endPage = Math.min(totalPages - 1, currentPage + Math.floor(maxVisiblePages / 2));

        if (startPage > 2) {
            const dots = document.createElement("li");
            dots.className = "page-item disabled";
            dots.innerHTML = `<span class="page-link">...</span>`;
            paginationContainer.appendChild(dots);
        }

        for (let i = startPage; i <= endPage; i++) {
            addPage(i);
        }

        if (endPage < totalPages - 1) {
            const dots = document.createElement("li");
            dots.className = "page-item disabled";
            dots.innerHTML = `<span class="page-link">...</span>`;
            paginationContainer.appendChild(dots);
        }

        if (totalPages > 1) addPage(totalPages);
    }

    searchButton.addEventListener("click", () => {
        currentPage = 1;
        fetchProducts(currentPage);
    });

    fetchProducts(currentPage); // Lần đầu load
});
