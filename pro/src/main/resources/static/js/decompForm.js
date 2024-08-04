
let fileList = [];
let decompFileLists = [];

function getFileName(path){
    const parts = path.split('/');
    return parts[parts.length - 1];
}

document.getElementById('fileInput').addEventListener('change', function() {
    const fileTableBody = document.querySelector('#fileTable tbody');
    fileTableBody.innerHTML = '';
    let i = 0;

    for (const file of this.files) {
        fileList = [];
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.textContent = `${file.name}`;
        row.appendChild(cell);
        fileTableBody.appendChild(row);

        const deleteButtonCell = document.createElement('td');
        const deleteButton = document.createElement('button');
        deleteButton.textContent = '×';
        deleteButton.classList.add('delete-button');
        deleteButton.value = i;
        deleteButton.addEventListener('click', function() {
            delete fileList[deleteButton.value];
            row.remove(); // 行を削除
        });
        deleteButtonCell.appendChild(deleteButton);
        row.appendChild(deleteButtonCell);

        fileTableBody.appendChild(row);

        fileList.push(file);
        i++;
    }
});

const compressBtn = document.getElementById('compressButton');
compressBtn.addEventListener('click',function(){
    compressBtn.disabled=true;
    const formData = new FormData();
    if (fileList.length == 0){
        return;
    }

    for(const file of fileList){
        if (file!==undefined){
            formData.append('fileContents',file);
        }
    }

    fetch('/decomp/upload',{
        method:'POST',
        body:formData
    })
    .then(res=>res.json())
    .then(data=>{
        decompFileLists = data;
        const fileTableBody = document.querySelector('#fileTable tbody');
        fileTableBody.innerHTML = '';
        for (const file of data) {
            const row = document.createElement('tr');
            const cell = document.createElement('td');
            cell.textContent = `${file}`;
            row.appendChild(cell);
            fileTableBody.appendChild(row);

            const downloadButtonCell = document.createElement('td');
            const downloadButton = document.createElement('button');
            const svgElem = document.createElement('img')
            svgElem.src = '../icon/download.svg';

            downloadButton.appendChild(svgElem);
            downloadButton.classList.add('decomp-download-button');
            downloadButton.value = file;
            downloadButton.addEventListener('click', function() {
                const formData= new FormData()
                formData.append('file',this.value);
                fetch('/decomp/download',{
                    method:'POST',
                    body:formData,
                })
                .then(res=>res.blob())
                .then(blob=>{
                    const url = URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href=url;
                    a.download = this.value;
                    document.body.appendChild(a);
                    a.click();
                    a.remove();
                    URL.revokeObjectURL(url);
                })
                .catch(err=>{
                    console.error(err);
                });
            });
            downloadButtonCell.appendChild(downloadButton);
            row.appendChild(downloadButtonCell);

            fileTableBody.appendChild(row);

        }
    })
    .catch(error=>{
        console.error('Error:',error);
    })
    .finally(()=>{
        compressBtn.disabled=false;
    });
});