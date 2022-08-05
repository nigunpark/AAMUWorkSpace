// function uploadFile(showImages) {
//     //이미지 업로드
//     let formData = new FormData(); // formData 객체를 생성한다.
//     for (let i = 0; i < showImages.length; i++) {
//       formData.append("multifiles", showImages[i]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다
//     }
//     return formData;
//   }

// let write = uploadFile(showImagesFile);
// bordWrite(
//   write,
//   title,
//   content,
//   detailRbn,
//   navigate,
//   postThemeNum,
//   setClickTab
// );

// function bordWrite(write, title, content, detailRbn, navigate, postThemeNum, setClickTab) {
//   write.append("id", sessionStorage.getItem("username"));
//   write.append("title", title);
//   write.append("content", content);
//   write.append("rbn", detailRbn);
//   write.append("themeid", postThemeNum);

//   let token = sessionStorage.getItem("token");
//   axios
//     .post("/aamurest/bbs/edit", write, {
//       headers: {
//         Authorization: `Bearer ${token}`,
//         "Content-Type": "multipart/form-data",
//       },
//     })
//     .then((resp) => {
//       // console.log(resp.data.result);
//       if (resp.data.result === "insertSuccess") {
//         alert("글이 저장되었습니다");
//         navigate("/forum");
//         // let bool = window.confirm("게시판으로 이동하겠습니까?");
//         // if (bool) navigate("/forum");
//         // if (!bool) setClickTab(0);
//       } else {
//         alert("저장오류가 발생했습니다. 관리자에게 문의하세요");
//         navigate("/");
//       }
//     })
//     .catch((error) => {
//       console.log(error);
//     });
// }

// const getPlanData = async () => {
//   try {
//     let token = sessionStorage.getItem("token");

//     let resp = await axios.get("/aamurest/planner/selectonemap", {
//       params: {
//         rbn: selectRbn,
//       },
//       headers: {
//         Authorization: `Bearer ${token}`,
//       },
//     });
//     // console.log("글 작성 페이지에서 상세경로 데이터 확인 : ", resp.data);
//     setDetailPostData(resp.data);
//     setDetailRbn(resp.data.rbn);
//     setDetailTitle(resp.data.title);

//     let keys = Object.keys(resp.data.routeMap);
//     let values = Object.values(resp.data.routeMap);
//     let keyValueData = Object.entries(resp.data.routeMap).map((val, idx) => {
//       return { [keys[idx]]: values[idx] };
//     });
//     setDetailRoute(keyValueData);
//   } catch (error) {
//     console.log((error) => console.log("상세경로 가져오기 실패", error));
//   }
// };

// useEffect(() => {
//     getPlanData();
//   }, []);
