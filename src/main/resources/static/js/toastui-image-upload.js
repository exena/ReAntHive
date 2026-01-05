async function toastuiImageUpload(blob, callback) {
    try {
        // 1. 에디터에 업로드한 이미지를 FormData 객체에 저장
        //    (이때, 컨트롤러 uploadEditorImage 메서드의 파라미터인 'image'와 formData에 append 하는 key('image')값은 동일해야 함)
        const formData = new FormData();
        formData.append('image', blob);
        // 2. FileApiController - uploadEditorImage 메서드 호출
        const response = await fetch(imgUrl + '/upload', {
            headers:{[csrfHeaderName]: csrfToken},
            method : 'POST',
            body : formData,
        });
        // 3. 컨트롤러에서 전달받은 디스크에 저장된 파일명
        const fileUrl = await response.text();
        // 4. addImageBlobHook의 callback 함수를 통해, 디스크에 저장된 이미지를 에디터에 렌더링
        callback(fileUrl, 'image alt attribute');
    } catch (error) {
        console.error('업로드 실패 : ', error);
    }
}