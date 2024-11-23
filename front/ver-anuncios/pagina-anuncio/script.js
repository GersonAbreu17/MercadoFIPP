let anuncio_id = localStorage.getItem("ad");

function carregarAnuncio() {
    const authToken = sessionStorage.getItem("authToken");

    const myHeaders = new Headers();
    myHeaders.append("Authorization", "Bearer " + sessionStorage.getItem("authToken"));

    const requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow"
    };

    fetch("http://localhost:8080/api/user/anuncios/get-one-anuncio?id=" + anuncio_id, requestOptions)
        .then((response) => response.json())
        .then((data) => {
            console.log(data)

            $("#titulo").text(data.title);
            $("#preco").html(`<strong>Preço: </strong> R$ ${data.price}`);
            $("#categoria").html(`<strong>Categoria: </strong> ${data.category.name}`);
            $("#descricao").html(`<strong>Descrição: </strong> ${data.descr}`);
            $("#data").html(`<strong>Data: </strong> ${formatarDataParaBR(data.date)}`);

            const fotosContainer = $("#fotos");
            fotosContainer.empty();
            data.fotos.forEach((foto, index) => {
                const isActive = index === 0 ? "active" : "";
                fotosContainer.append(`
                    <div class="carousel-item ${isActive}">
                        <img src="../../uploads/${foto.filename}" class="d-block w-100" alt="Imagem do anúncio">
                    </div>
                `);
            });
            carregarPerguntas(data.perguntas)
        })
        .catch((error) => console.error(error));

    if (!authToken) {
        console.error("Token de autenticação não encontrado.");
        return;
    }
    
    
}


function formatarDataParaBR(dataISO) {
    const data = new Date(dataISO); // Cria um objeto Date
    const dia = String(data.getDate()).padStart(2, '0'); // Dia com 2 dígitos
    const mes = String(data.getMonth() + 1).padStart(2, '0'); // Mês com 2 dígitos
    const ano = data.getFullYear(); // Ano com 4 dígitos
    return `${dia}/${mes}/${ano}`;
}

function carregarPerguntas(perguntas){
    console.log(perguntas)

    $(".perguntas").empty();

    for(let pergunta of perguntas)
    {
        $(".perguntas").append(
            `<div class="pergunta mb-3">
                <p class="questao">
                    <strong>Cliente: </strong> 
                    ${pergunta.text != null ? pergunta.text : ""}
                </p>
                <p class="resposta">
                    <strong>Vendedor: </strong>
                    ${pergunta.resp != null ? pergunta.resp : ""}
                </p>
            </div>`
        )
    }
}

function perguntar(){
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", "Bearer "+sessionStorage.getItem("authToken"));

    const raw = JSON.stringify({
        "pergunta": $("#perguntar").val(),
        "idAnuncio": anuncio_id
    });

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    fetch("http://localhost:8080/api/user/anuncios/addPerguntas?token=" + sessionStorage.getItem("authToken"), requestOptions)
    .then((response) => response.text())
    .then((result) => {
        console.log(result)
        carregarAnuncio();
        $("#perguntar").val("");
    })
    .catch((error) => console.error(error));
}

(function inicializar(){
    carregarAnuncio();
})();