let nivel = sessionStorage.getItem("nivel");

if(nivel == 1)
{
    $('.acesso-1').removeClass("d-none");
    $('.acesso-2').removeClass("d-none");
    $('.acesso-3').removeClass('d-none');
}
else if(nivel == 2)
{
    $('.acesso-2').removeClass("d-none");
    $('.acesso-3').removeClass('d-none');
}
else if(nivel == 3)
{
    $('.acesso-3').removeClass('d-none');
}

const requestOptions = {
    method: "GET",
    redirect: "follow"
  };
  
  fetch("http://localhost:8080/access/verificacao?token="+sessionStorage.getItem("authToken"), requestOptions)
    .then((response) => response.json())
    .then((result) => {
        console.log(result)
        if(result != null)
        {
            sessionStorage.setItem("name", result.name);
            sessionStorage.setItem("nivel", result.level);
        }
    })
    .catch(
        (error) => {console.log(error)
        if(window.location.href != "http://127.0.0.1:5500/index.html")
        window.location.href = "http://127.0.0.1:5500/login/pagina.html"
    });