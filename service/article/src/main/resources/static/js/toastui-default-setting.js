//script th:inline="javascript"
//const csrfHeaderName = [[${_csrf.headerName}]];
//const csrfToken = [[${_csrf.token}]];
//const imgUrl = [[${imageUrl}]];
//const postContent = [[${postForm.content}]];

const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    hooks: {
        async addImageBlobHook(blob, callback) { // 이미지 업로드 로직 커스텀
            await toastuiImageUpload(blob, callback);
        }
    }
});
if(postContent){
    editor.setMarkdown(postContent);
}

document.getElementById('entireForm').addEventListener('submit', function (event) {
    // 에디터에서 작성된 내용을 가져옵니다.
    const content = editor.getMarkdown();

    const encodedContent = encodeURIComponent(content);

    // 숨겨진 input 태그에 값을 넣습니다.
    document.getElementById('editor-content-provider').value = encodedContent;
});