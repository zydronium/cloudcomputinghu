var y; //global
var oddeven = 0;
function meerInfo(x)
{
    //zichtbaar maken van div
    y = document.getElementById('competenties');
    y.style.display = 'block';
    //een tekstbuffertje t vullen met HTML en inhoud voor div
    var t = ""
    
    nodes = x.getElementsByClassName('studentnr');
    var studentnr = nodes[0].innerHTML;
    t += "studentnummer: " + studentnr + "<br />";
    
    nodes = x.getElementsByClassName('voornaam');
    var voornaam = nodes[0].innerHTML;
    t += "voornaam: " + voornaam + "<br />";
    
    nodes = x.getElementsByClassName('achternaam');
    var achternaam = nodes[0].innerHTML;
    t += "achternaam: " + achternaam + "<br />";
    
    nodes = x.getElementsByClassName('email');
    var email = nodes[0].innerHTML;
    t += "email: " + email + "<br />";
    
    nodes = x.getElementsByClassName('geboortedatum');
    var geboortedatum = nodes[0].innerHTML;
    t += "geboortedatum: " + geboortedatum + "<br />";
    
    nodes = x.getElementsByClassName('startdatum');
    var startdatum = nodes[0].innerHTML;
    t += "startdatum: " + startdatum + "<br />";
    
    nodes = x.getElementsByClassName('einddatum');
    var einddatum = nodes[0].innerHTML;
    t += "einddatum: " + einddatum + "<br />";
    
    nodes = x.getElementsByClassName('bedrijfsnaam');
    var bedrijfsnaam = nodes[0].innerHTML;
    t += "bedrijfsnaam: " + bedrijfsnaam + "<br />";
    
    nodes = x.getElementsByClassName('begeleider');
    var begeleider = nodes[0].innerHTML;
    t += "begeleider: " + begeleider + "<br />";
    
    nodes = x.getElementsByClassName('telefoon');
    var telefoon = nodes[0].innerHTML;
    t += "telefoon: " + telefoon + "<br />";
    
    nodes = x.getElementsByClassName('data');
    var data = nodes[0];
    
    nodes = data.getElementsByClassName('competence1');
    var competence1 = nodes[0].innerHTML;
    t += "Competentie1: <input min='1' max='10' type='range' id='competence1' value='" + competence1 + "' /><br />";
    
    nodes = data.getElementsByClassName('competence2');
    var competence2 = nodes[0].innerHTML;
    t += "Competentie2: <input min='1' max='10' type='range' id='competence2' value='" + competence2 + "' /><br />";
    
    nodes = data.getElementsByClassName('competence3');
    var competence3 = nodes[0].innerHTML;
    t += "Competentie3: <input min='1' max='10' type='range' id='competence3' value='" + competence3 + "' /><br />";
    
    nodes = data.getElementsByClassName('competence4');
    var competence4 = nodes[0].innerHTML;
    t += "Competentie4: <input min='1' max='10' type='range' id='competence4' value='" + competence4 + "' /><br />";
    //t += "TODO: hier komen de competentieregelaars voorafgegaan door info overstudent (nr, voornaam en achternaam minimaal) en met een korte beschrijving percompetentie";
    y.innerHTML = t;
}
function deleteRij(x)
{
    var r = confirm("Weet je het zeker?");
    if (r == true) {
        var table = document.getElementById("tabel");
        table.deleteRow(x);
    }
    
}
function voegToe()
{
    var table = document.getElementById("tabel");
    var row = table.insertRow( - 1); //-1 betekent: voegt toe achter laatste bestaande rij
    if(oddeven == 0) {
        oddeven = 1;
    }else{
        oddeven = 0;
        row.style.backgroundColor = '#DCDCDC';
    }
    r = table.rows.length;
    for (i = 0; i < document.forms[0].elements.length - 1; i++)
    {
        var x = row.insertCell(i);
        x.innerHTML = document.forms[0].elements[i].value;
        x.style.cursor = 'pointer';
        x.className = document.forms[0].elements[i].id;
        //De meerInfo() functie wordt als eventhandler voor onclick aan een cel toegewezen
        x.onclick = function(){
            meerInfo(row);
        }
    }
    var x = row.insertCell(-1);
    x.innerHTML = "";
    x.style.cursor = 'pointer';
    x.style.display = 'none';
    x.className = "data";
    x.innerHTML = "<span class='competence1'>6</span><span class='competence2'>8</span><span class='competence3'>3</span><span class='competence4'>5</span>"
    //De meerInfo() functie wordt als eventhandler voor onclick aan een cel toegewezen
    x.onclick = function(){
        meerInfo(row);
    }
    var x = row.insertCell(-1);
    x.innerHTML = "";
    x.style.cursor = 'pointer';
    x.className = "verwijder";
    x.innerHTML = "[Verwijder rij]"
    //De meerInfo() functie wordt als eventhandler voor onclick aan een cel toegewezen
    x.onclick = function(){
        deleteRij(row);
    }
}