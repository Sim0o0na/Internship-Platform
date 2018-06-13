function label_value()
{
    var src = document.getElementById('in1').value;
    document.getElementById('label1').innerHTML = "<span class='glyphicon glyphicon-upload'></span>"+src;
}