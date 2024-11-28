let tbody = $("tbody");
let table = $("table");
let pesquisa = $("#pesquisa");

let addNomeCategoria = $("#addNomeCategoria");
let editarNomeCategoria = $("#editarNomeCategoria");
let editarIdCategoria = $("#editarIdCategoria");

let fecharAdd = $("#fecharAdd");
let fecharEditar = $("#fecharEditar");
let btnAbrirModalEditar = $("#btnAbrirModalEditar");

//inicializar
carregarTabela();


function carregarTabela() {
    const requestOptions = {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${sessionStorage.getItem("authToken")}` // Corrigido para enviar o token no cabeçalho
        },
        redirect: "follow"
    };

    tbody.html('');

    fetch("http://localhost:8080/api/admin/categorias/get-many?filtro=" + pesquisa.val(), requestOptions)
        .then((response) => response.json())
        .then((result) => {
            console.log(result)
            result.forEach((e) => {
                tbody.html(tbody.html() + `
                    <tr>
                        <td style="text-align: center;"> <label for="ck_${e.id}" style="width: 100%;"><input id="ck_${e.id}" input type="checkbox" data-id="${e.id}"/></label></td>
                        <td>${e.id}</td>
                        <td>${e.name.toUpperCase()}</td>
                    </tr>
                `)
            });
        })
        .catch((error) => console.error(error));
}

function getSelecionados() {
    let lista = [];
    $('input[type="checkbox"]:checked').each(function () {
        lista.push($(this).data('id'));
    })
    return lista;
}

function deletar() {
    let promises = [];

    let selecionados = getSelecionados();
    if (selecionados.length > 0) {

        const requestOptions = {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("authToken")}` 
            },
            redirect: "follow"
        };

        selecionados.forEach(id => {
            promises.push(new Promise((resolve) => {
                fetch("http://localhost:8080/api/admin/categorias/delete?id=" + id, requestOptions)
                    .then((response) => response.text())
                    .then((result) => {
                        console.log(result);
                        resolve();
                    })
                    .catch((error) => {
                        console.error(error);
                        resolve();
                    });
            }));
        });
    } else {
        console.log("Não há o que deletar");
    }

    Promise.all(promises).then(carregarTabela);
}

function adicionar() {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", `Bearer ${sessionStorage.getItem("authToken")}`)

    let raw;
    if(editarIdCategoria.val().length == 0){
        raw = JSON.stringify({
            "name": addNomeCategoria.val()
        });
    }
    else{
        raw = JSON.stringify({
            "id": editarIdCategoria.val(),
            "name": editarNomeCategoria.val()
        });
    }

    console.log(addNomeCategoria.val());

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    fetch("http://localhost:8080/api/admin/categorias/add", requestOptions)
        .then((response) => response.text())
        .then((result) => {
            console.log(result);
            fecharAdd.click();
            fecharEditar.click();
            limparCampos();
            carregarTabela();

        }).catch((error) => {
            console.error(error);
            fecharAdd.click();
            fecharEditar.click();
            limparCampos();
            carregarTabela();
        });
}

function abrirModalEditar() {
    let selecionados = getSelecionados();

    if(selecionados.length > 1)
        alert("Selecione somente um registro!");
    else if(selecionados.length < 1)
        alert("Selecione pelo menos um registro!");
    else{
        const requestOptions = {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("authToken")}` // Corrigido para enviar o token no cabeçalho
            },
            redirect: "follow"
        };

        fetch("http://localhost:8080/api/admin/categorias/get-one?id=" + selecionados[0], requestOptions)
            .then((response) => response.json())
            .then((result) => {
                editarIdCategoria.val(result.id);
                editarNomeCategoria.val(result.name);

                btnAbrirModalEditar.click();

            })
            .catch((error) => console.error(error));

    }
}

function limparCampos(){
    addNomeCategoria.val('')
    editarNomeCategoria.val('')
    editarIdCategoria.val('')
}