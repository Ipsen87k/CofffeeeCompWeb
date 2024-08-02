
let fileList = [];

document.getElementById('fileInput').addEventListener('change', function() {
    const fileTableBody = document.querySelector('#fileTable tbody');

    for (const file of this.files) {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.textContent = `${file.name}`;
        row.appendChild(cell);
        fileTableBody.appendChild(row);

        const deleteButtonCell = document.createElement('td');
        const deleteButton = document.createElement('button');
        deleteButton.textContent = '×';
        deleteButton.classList.add('delete-button');
        deleteButton.addEventListener('click', function() {
            fileList=fileList.filter(v=> v.name != row.innerText.slice(0,row.innerText.length-1));
            row.remove(); // 行を削除
        });
        deleteButtonCell.appendChild(deleteButton);
        row.appendChild(deleteButtonCell);

        fileTableBody.appendChild(row);

        fileList.push(file);
    }
});

document.getElementById('compressButton').addEventListener('click',function(){
    const formData = new FormData();
    for(const file of fileList){
        formData.append('fileContents',file);
    }

    fetch('/comp/upload',{
        method:'POST',
        body:formData
    })
    .then(data=>{
        const elem = document.getElementById('restAction');
        const downloadElemObj = document.getElementById('download_a')
        if (downloadElemObj === null){
            const downloadElem = document.createElement('a');
            downloadElem.textContent = 'Download';
            downloadElem.href = '/comp/download';
            downloadElem.className = 'download-button';
            downloadElem.id = 'download_a'

            elem.appendChild(downloadElem);
        }
    })
    .catch(error=>{
        console.error('Error:',error);
    });
});