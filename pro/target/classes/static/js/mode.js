

document.getElementById('compressionType').addEventListener('change',function(e){
    const selectedValue = e.target.value;
    console.info(selectedValue);
    fetch('/mode/'+selectedValue,{
        method:'GET'
    }).catch(e=>{console.error(e)});
});