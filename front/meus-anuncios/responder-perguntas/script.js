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
                fotosContainer.empty();
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
        let htmlResposta;


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
                <div class="perguntar acesso-2 mt-3">
                    <textarea class="form-control" type="text" name="perguntar" id="perguntar"></textarea>
                    <button onclick="responder(${pergunta.id})" class="btn-perguntar btn btn-primary"><i class="fa-solid fa-paper-plane"></i></button>
                </div>
            </div>`
        )
    }
}

(function inicializar(){
    carregarAnuncio();
})();

function responder(id)
{
    
}