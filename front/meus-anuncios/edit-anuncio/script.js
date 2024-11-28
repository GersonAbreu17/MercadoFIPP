let titulo = $("#titulo");
let data = $("#data");
let preco = $("#preco");
let categoria = $("#categoria");
let descricao = $("#descricao");
let fotos = $("#fotos");
let anuncio_id = localStorage.getItem("ad");
let idSelecionado = 0;

function carregarCategorias() {
    categoria.html("");
    const requestOptions = {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${sessionStorage.getItem("authToken")}` // Corrigido para enviar o token no cabeçalho
        },
        redirect: "follow"
    };

    fetch("http://localhost:8080/api/vendedor/meusanuncios/get-many-categorias", requestOptions)
        .then((response) => response.json())
        .then((result) => {
            console.log(result)
            let selected;

            for(let element of result){
                
                if(result.id == idSelecionado)
                    selected = "selected";
                else
                    selected = "";

                categoria.html(categoria.html()+`
                    <option ${selected} value="${element.id}">${element.name.toUpperCase()}</option>
                `)
            }
        })
        .catch((error) => console.error(error));
}

async function adicionarAnuncio() {
    const titulo = document.querySelector('#titulo').value;
    const data = document.querySelector('#data').value; 
    const preco = document.querySelector('#preco').value;
    const categoria = document.querySelector('#categoria').value; 
    const descricao = document.querySelector('#descricao').value;
    const fotos = document.querySelector('#fotos').files;

    const anuncioDTO = {
        idAnuncio: parseInt(anuncio_id),
        titulo: titulo,
        data: data,
        preco: parseFloat(preco),
        categoria: categoria,
        descricao: descricao,
        usuario: sessionStorage.getItem("authToken") 
    };

    const formData = new FormData();
    formData.append("anuncio", new Blob([JSON.stringify(anuncioDTO)], { type: "application/json" }));

    if(fotos.length > 0)
        for (let i = 0; i < fotos.length; i++) {
            formData.append("fotos", fotos[i]);
        }
    else
        return alert("Você precisa adicionar pelo menos uma foto.");

    try {
        const response = await fetch("http://localhost:8080/api/vendedor/meusanuncios/update-anuncio", {
            method: "PUT",
            body: formData,
            headers: {
                "Authorization": "Bearer " + sessionStorage.getItem("authToken")
            },
            redirect: "follow"
        });

        if (response.ok) {
            const resultado = await response.json();
            alert("Anuncio alterado com sucesso");
            document.querySelector("#formAnuncio").reset(); 
        } else {
            const erro = await response.text();
            alert("Erro ao editar anúncio: " + erro);
        }
    } catch (err) {
        console.error("Erro ao enviar requisição:", err);
        alert("Erro inesperado ao editar anúncio.");
    }
}


function validarCampos() {
    $(".pErro").text("");

    let isValid = true;

    if (titulo.val().trim() === "") {
        $("#pErroTitulo").text("O campo Título é obrigatório.");
        isValid = false;
    }

    if (data.is(":enabled") && data.val().trim() === "") {
        $("#pErroData").text("O campo Data é obrigatório.");
        isValid = false;
    }

    if (preco.val().trim() === "" || parseFloat(preco.val()) <= 0) {
        $("#pErroPreco").text("Insira um preço válido (maior que 0).");
        isValid = false;
    }

    if (categoria.val() == "0" || categoria.val() === null) {
        $("#pErroCategoria").text("Selecione uma categoria.");
        isValid = false;
    }

    if (descricao.val().trim() === "") {
        $("#pErroDescricao").text("O campo Descrição é obrigatório.");
        isValid = false;
    }

    if(fotos[0].files.length > 3)
    {
        console.log($("#pErroFotos"))
        $("#pErroFotos").text("Você só pode enviar no máximo 3 fotos");
    }

    return isValid;
}

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
        .then((result) => {
            console.log(result)

            titulo.val(result.title)
            data.val(result.date)
            descricao.val(result.descr)
            preco.val(result.price)
            categoria.val(result.category.name)
            idSelecionado = result.category.id;
            
            carregarCategorias();

        })
        .catch((error) => console.error(error));

    if (!authToken) {
        console.error("Token de autenticação não encontrado.");
        return;
    }
}

carregarAnuncio();
