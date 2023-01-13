let newImmobile = new Array();//elementi da aggiungere al DB
let deleteImmobile = new Array();//elementi da rimuovere dal DB

window.addEventListener("load", function(e) {

    let owner=document.querySelector('tr:last-child td:last-child')
    let idInterno=document.querySelector('tr:last-child td:first-child')

    if(idInterno!=null)
        idInterno=+idInterno.textContent+1//cast
    else
        idInterno=1;

    /*evento tasto Aggiungi immobile pagina: properties.html*/
    const butAdd = document.getElementById("aggiungi");
    butAdd.addEventListener("click", function(event){
        let canSubmit = true;

        const txtMq = document.getElementById("mq")

        if (txtMq.value.trim()==""){
            alert("Attenzione! il campo metri quadri non puo' essere vuoto e deve contenere un numero")
            canSubmit = false;
        }
        const txtImmobile = document.getElementById("immobile")
        if(!canSubmit){
            e.preventDefault();
        }
        else{
            //aggiungo in tabella principale:
                const table = document.querySelectorAll("table");

                const newTr = document.createElement("tr");

                newTr.setAttribute("id","riga_"+idInterno)

                const newTdId = document.createElement("td");
                const newTdTipo = document.createElement("td");
                const newTdMq = document.createElement("td");

                newTdId.appendChild( document.createTextNode(idInterno.toString()));
                newTdTipo.appendChild( document.createTextNode(txtImmobile.value));
                newTdMq.appendChild( document.createTextNode(txtMq.value));

                newTr.appendChild(newTdId)
                newTr.appendChild(newTdTipo)
                newTr.appendChild(newTdMq)
                newTr.className="new"
                table[0].appendChild(newTr);

            //aggiungo in tabella Rimuovi Immobile:
                const newTr1 = document.createElement("tr");

                const newTdSelect=document.createElement("td");
                const newTdId1 = document.createElement("td");
                const newTdTipo1 = document.createElement("td");
                const newTdMq1 = document.createElement("td");

                var check=document.createElement('input');
                check.setAttribute("id",idInterno.toString())
                check.setAttribute('type', 'checkbox')
                newTdSelect.appendChild(check);

                newTdId1.appendChild( document.createTextNode(idInterno+"(new*)"));
                newTdTipo1.appendChild( document.createTextNode(txtImmobile.value));
                newTdMq1.appendChild( document.createTextNode(txtMq.value));

                newTr1.appendChild(newTdSelect)
                newTr1.appendChild(newTdId1)
                newTr1.appendChild(newTdTipo1)
                newTr1.appendChild(newTdMq1)
                newTr1.className="new"
                table[1].appendChild(newTr1);


                const newImmo = {"type": txtImmobile.value,"mq": txtMq.value,};
                newImmobile[idInterno]=newImmo
                idInterno++
        }

    });

    /*evento tasto Aggiungi immobile pagina: properties.html*/
    const butRimuovi = document.getElementById("rimuovi");
    butRimuovi.addEventListener("click", function(event){

        //immobili selezionati
        var immoSelezionati = document.querySelectorAll("input:checked");
        if(immoSelezionati.length==0){
            alert("Devi selezionare almeno un immobile per poter procedere, se non vuoi cancellare nulla clicca su chiudi")
        }

        else{
            immoSelezionati.forEach(function (i){

                if(i.getAttribute("id")!=null) {
                    console.log("entro nell'if perchè ho id")
                    var idImmo = i.getAttribute("id")
                    var idRiga = "riga_" + idImmo;
                    var riga = document.querySelector("#" + idRiga);//seleziono riga_id
                    riga.style.display="none"
                    console.log("Sto eliminando " + idImmo)
                    let num = parseInt(idImmo)
                    console.log("Elimino " + newImmobile[num]);
                    delete newImmobile[num]
                }

                else {
                    var idImmo = i.getAttribute("value")
                    var idRiga = "riga_" + idImmo;
                    var idCol = "col_" + idImmo;
                    //prendo la riga selezionata
                    var riga = document.querySelector("#" + idRiga);
                    riga.className = "delete";//assegno classe
                    var col = document.querySelector("#" + idCol);
                    col.textContent = idImmo + " (canc*)"
                    console.log("id da cancellare: " + idImmo);
                    deleteImmobile.push(idImmo);
                }
            });

            alert("Le modifiche effettuate non sono ancora del tutto persistenti, cliccare su salva per renderle definitive")
        }
    });


    /*evento tasto Salva immobile pagina: properties.html*/
    const butSave = document.getElementById("salva");
    butSave.addEventListener("click", function(event){

        //togliere dal db
        if(deleteImmobile.length!=0) {
            console.log("Dentro canc")

            json = JSON.stringify(deleteImmobile);
            $.ajax({
                "method": "post",
                "url": "/deleteProperty",
                "data": json,
                "contentType": "application/json",
                "success": function (risp) {

                    var allTrs = document.querySelectorAll(".delete");
                    allTrs.forEach(function (elem) {
                        console.log("elemcanc: "+ elem.getAttribute("value"))
                        elem.style.display="none"
                        deleteImmobile=[];
                        console.log(deleteImmobile.length)
                    });
                }
            });

        }

        //aggiungere nel db
        if(newImmobile.length!=0) {
            console.log("Dentro Add")

            let json = JSON.stringify(newImmobile);
            $.ajax({
                "method": "post",
                "url": "/newProperty",
                "data": json,
                "contentType": "application/json",
                "success": function (risp) {

                    var allTrs = document.querySelectorAll("tr");
                    allTrs.forEach(function (elem) {
                        console.log("elemadd: "+elem.getAttribute("value"))
                        elem.className=""
                        let owner=document.querySelector('elem td:last-child')
                        newImmobile=[];
                        console.log(newImmobile.length)
                    });
                }
            });
        }
        if(deleteImmobile.length==0 && newImmobile.length==0){
            alert("Non e' prensete nessuna modifica sul sistema, non e' necessario salvare")
        }
    });
});


