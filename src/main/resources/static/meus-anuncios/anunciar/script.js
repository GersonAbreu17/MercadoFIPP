let titulo = $("#titulo");
let data = $("#data");
let preco = $("#preco");
let categoria = $("#categoria");
let descricao = $("#descricao");
let fotos = $("#fotos");

(function carregarData() {
    let date = new Date();
    let ano = date.getFullYear();
    let mes = date.getMonth() + 1;
    let dia = date.getDate();
    mes = mes < 10 ? '0' + mes : mes;
    dia = dia < 10 ? '0' + dia : dia;
    document.getElementById('data').value = `${ano}-${mes}-${dia}`;
})();

(function carregarCategorias() {
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
            categoria.html(`<option value="0" selected disabled hidden>Selecione</option>`);
            for(let element of result){
                categoria.html(categoria.html()+`
                    <option value="${element.id}">${element.name.toUpperCase()}</option>
                `)
            }
        })
        .catch((error) => console.error(error));
})()

async function adicionarAnuncio() {

    const titulo = document.querySelector('#titulo').value;
    const data = document.querySelector('#data').value; 
    const preco = document.querySelector('#preco').value;
    const categoria = document.querySelector('#categoria').value; 
    const descricao = document.querySelector('#descricao').value;
    const fotos = document.querySelector('#fotos').files;

    const anuncioDTO = {
        titulo: titulo,
        data: data,
        preco: parseFloat(preco),
        categoria: categoria,
        descricao: descricao,
        usuario: sessionStorage.getItem("authToken") 
    };

    const formData = new FormData();
    formData.append("anuncio", new Blob([JSON.stringify(anuncioDTO)], { type: "application/json" }));

    for (let i = 0; i < fotos.length; i++) {
        formData.append("fotos", fotos[i]);
    }

    try {
        const response = await fetch("http://localhost:8080/api/vendedor/meusanuncios/criarAnuncio", {
            method: "POST",
            body: formData,
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("authToken")}` 
            },
            redirect: "follow"
        });

        if (response.ok) {
            const resultado = await response.text();
            alert(resultado);
            document.querySelector("#formAnuncio").reset(); 
        } else {
            const erro = await response.text();
            alert("Erro ao criar anúncio: " + erro);
        }
    } catch (err) {
        console.error("Erro ao enviar requisição:", err);
        alert("Erro inesperado ao criar anúncio.");
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