

document.getElementById('compressionType').addEventListener('change',function(e){
    const selectedValue = e.target.value;
    fetch('/mode/'+selectedValue,{
        method:'GET'
    })
    .then(_data=>{
        let downloadButtonElem = document.getElementById('download_a');
        if (downloadButtonElem!==null){
            downloadButtonElem.remove();
        }
    })
    .catch(e=>{console.error(e)});
});