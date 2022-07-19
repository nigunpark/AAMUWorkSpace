import React, { useState, useEffect } from "react";
import axios from "axios"; // 액시오스
import { useLocation } from "react-router-dom";
import { saveAs } from "file-saver";
import * as ExcelJS from "exceljs";
const Test = () => {
  let token = sessionStorage.getItem("token");

  function testAxios() {
    axios
      .post("http://192.168.0.19:8080/rest/info2?areacode=1&contenttypeid=12")
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      })
      .then(() => {
        console.log("get요청실행됨");
      });
  }

  //get,del은 두번째 header post,put은 세번째에
  function test() {
    axios
      .get("/aamurest/info/places", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          areacode: "1",
          contenttypeid: "12",
        },
      })
      .then((resp) => {
        console.log("get성공");
        console.log(resp.data);
      })
      .catch((error) => {
        console.log("error :%O ", error);
      })
      .then(() => {
        console.log("get요청됨");
      });
  }
  return (
    <div>
      <button
        style={{ background: "green", cursor: "pointer" }}
        onClick={() => testAxios()}
      >
        test
      </button>
      <button
        style={{ background: "yellow", cursor: "pointer" }}
        onClick={() => test()}
      >
        우재한테 get요청
      </button>
      <button onClick={handleExcel}>엑셀 내보내기!!</button>
    </div>
  );
};
const handleExcel = async () => {
  const workBook = new ExcelJS.Workbook();
  const workSheet = workBook.addWorksheet("MyTravel"); // sheet 이름이 My Sheet
  //타이틀
  workSheet.mergeCells("B1:F1");
  workSheet.getCell("B1").value = "제주도 3박 4일 여행";
  workSheet.getCell("B1").font = { size: 16, bold: true };
  workSheet.getCell("B1").alignment = { horizontal: "center" };
  //기간
  workSheet.mergeCells("B2:F2");
  workSheet.getCell("B2").value = "8월 1일 ~ 8월 4일";
  workSheet.getCell("B2").alignment = { horizontal: "center" };
  //Days map으로 돌려야 할듯??
  workSheet.getCell("A5").value = "DAY 1";
  workSheet.getCell("A5").font = { size: 16, bold: true };
  workSheet.getCell("A5").fill = {
    type: "pattern",
    pattern: "solid",
    fgColor: { argb: "FFFAA307" },
    bgColor: { argb: "FFFFFFFF" },
  };
  workSheet.mergeCells("B5:C5");
  workSheet.getCell("B5").value = "8월 1일";
  workSheet.getCell("B5").fill = {
    type: "pattern",
    pattern: "solid",
    fgColor: { argb: "FFFAA307" },
    bgColor: { argb: "FFFFFFFF" },
  };
  workSheet.getRow(6).values = [
    "도착시간",
    "출발시간",
    "유형",
    "장소",
    "주소",
    "메모",
  ];

  // sheet 데이터 설정
  // workSheet.columns = [
  //   { key: "name", width: 40 },
  //   { key: "gender", hidden: false, width: 30 },
  //   { key: "deptCode", width: 60 },
  //   {
  //     key: "deptName",
  //     width: 100,
  //     // 스타일 설정
  //     style: {
  //       // Font 설정
  //       font: { name: "Arial Black", size: 20 },
  //       // Borders 설정
  //       border: {
  //         top: { style: "thin", color: { argb: "FF00FF00" } },
  //         left: { style: "thin", color: { argb: "FF00FF00" } },
  //         bottom: { style: "thin", color: { argb: "FF00FF00" } },
  //         right: { style: "thin", color: { argb: "FF00FF00" } },
  //       },
  //       // Fills 설정
  //       fill: {
  //         type: "pattern",
  //         fgColor: { argb: "FFFFFF00" },
  //         bgColor: { argb: "FF0000FF" },
  //       },
  //     },
  //   },
  // ];

  // workSheet.addRow({
  //   name: "John Doe",
  //   gender: "남",
  //   deptCode: 12,
  //   deptName: "인사팀",
  // });
  // workSheet.addRow({
  //   name: "Jane Doe",
  //   gender: "여",
  //   deptCode: 13,
  //   deptName: "홍보팀",
  // });

  // 다운로드
  const mimeType = {
    type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  };
  const buffer = await workBook.xlsx.writeBuffer();
  const blob = new Blob([buffer], mimeType);
  saveAs(blob, "MyTravel.xlsx");
};

export default Test;
