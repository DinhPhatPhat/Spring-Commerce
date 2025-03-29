document.addEventListener("DOMContentLoaded", function () {
    const storiesContainer = document.querySelector(".row.gy-5");
    const paginationContainer = document.querySelector(".pagination"); // Đảm bảo chọn <ul class="pagination">

    const searchInput = document.getElementById("search-input");
    const searchButton = document.getElementById("search-button");

    let currentPage = 1;
    let currentSearch = "";

    function fetchStories(page = 1, search = "") {
        axios.get("/api/story", {
            params: { page, search }
        })
            .then(response => {
                const { stories, totalPages } = response.data;
                renderStories(stories);
                renderPagination(totalPages, page);
            })
            .catch(error => console.error("Lỗi tải dữ liệu:", error));
    }

    function renderStories(stories) {
        storiesContainer.innerHTML = stories.map(story => `
            <div class="col-lg-4 col-md-6">
                <article>
                    <div class="post-img">
                        <a href="/story/${story.meta}">
                            <img src="${story.imagePath || '/image/story/default.jpg'}" alt="" class="img-fluid thumbnail">
                        </a>
                    </div>
                    <div class="meta-top">
                        <ul>
                            <li class="d-flex align-items-center"><i class="bi bi-dot"></i>
                                <a href="/story/${story.meta}">
                                   ${story.user.name}
                                </a>
                            </li>
                            <li class="d-flex align-items-center"><i class="bi bi-dot"></i>
                                <a href="/story/${story.meta}">
                                    <time datetime="${story.updatedAt}">${formatDate(story.updatedAt)}</time>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <h2 class="title">
                        <a href="/story/${story.meta}">${story.title}</a>
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
            a.className = page === currentPage ? "active" : "";
            a.addEventListener("click", (e) => {
                e.preventDefault();
                if (currentPage !== page) {
                    currentPage = page;
                    fetchStories(currentPage, currentSearch);
                }
            });
            li.appendChild(a);
            paginationContainer.appendChild(li);
        };

        // Always show the first page
        if (totalPages > 1) addPage(1);

        let startPage = Math.max(2, currentPage - Math.floor(maxVisiblePages / 2));
        let endPage = Math.min(totalPages - 1, currentPage + Math.floor(maxVisiblePages / 2));

        if (startPage > 2) {
            paginationContainer.appendChild(document.createTextNode("..."));
        }

        for (let i = startPage; i <= endPage; i++) {
            addPage(i);
        }

        if (endPage < totalPages - 1) {
            paginationContainer.appendChild(document.createTextNode("..."));
        }

        // Always show the last page
        if (totalPages > 1) addPage(totalPages);
    }




    function formatDate(dateString) {
        const date = new Date(dateString);
        return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()}`;
    }

    searchButton.addEventListener("click", () => {
        currentSearch = removeVietnameseTones(searchInput.value.trim());
        currentPage = 1;
        fetchStories(currentPage, currentSearch);
    });

    fetchStories();

    function removeVietnameseTones(str) {
        return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase();
    }
});
