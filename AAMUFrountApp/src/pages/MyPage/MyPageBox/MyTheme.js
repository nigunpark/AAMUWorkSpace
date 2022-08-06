import axios from "axios";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import MyThemeLists from "./MyThemeLists";

const MyTheme = ({ checkeds, setCheckeds }) => {
  const [themeItem, setThemeItem] = useState([]);

  const [theme, setTheme] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const onClickModal = () => {
    setIsOpen(true);
    // alert("테마는 최대 5개까지 저장되요!");
  };

  const getThemes = async () => {
    let token = sessionStorage.getItem("token");
    await axios
      .get("/aamurest/users/theme", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        setThemeItem(resp.data);
      })
      .catch((err) => {
        console.log("err", err);
      });
  };

  const getMyThemes = async () => {
    let token = sessionStorage.getItem("token");
    await axios
      .get("/aamurest/user/theme", {
        params: {
          id: sessionStorage.getItem("username"),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("나의 테마 : ", resp.data);
        setTheme(resp.data);
      })
      .catch((error) => {
        console.log("나의 테마 가져오기 실패", error);
      });
  };

  useEffect(() => {
    getThemes();
    getMyThemes();
    // setTheme(checkeds);
  }, []);

  const getCheckedBoxes = (checked, val) => {
    if (checked) {
      setCheckeds([...checkeds, val]);
      setTheme([...theme, val]);
    } else {
      setCheckeds(checkeds.filter((one) => one !== val));
      setTheme(theme.filter((one) => one !== val));
    }
  };
  const doCheck = (e) => {
    let checkbox = e.target.parentElement.nextSibling.lastChild;
    if (checkbox.checked) {
      checkbox.checked = false;
      e.target.parentElement.parentElement.style.background = "white";
      e.target.parentElement.parentElement.style.color = "black";
    } else {
      checkbox.checked = true;
      e.target.parentElement.parentElement.style.background = "var(--skyblue)";
      e.target.parentElement.parentElement.style.color = "white";
    }
    if (checkeds.length === 5) {
      alert("테마는 최대 5개까지 저장되요!");
      return false;
    }
    return true;
  };

  return (
    <div
      className="projects-theme"
      onClick={(e) => {
        e.stopPropagation();
        onClickModal();
        setTheme([]);
        setCheckeds([]);
      }}
    >
      <MyThemeLists theme={theme} />

      {isOpen == true ? (
        <ThemeSelect
          setIsOpen={setIsOpen}
          getCheckedBoxes={getCheckedBoxes}
          doCheck={doCheck}
          themeItem={themeItem}
          checkeds={checkeds}
          setCheckeds={setCheckeds}
        />
      ) : null}
    </div>
  );
};

function ThemeSelect({ setIsOpen, getCheckedBoxes, doCheck, themeItem, checkeds, setCheckeds }) {
  const themeEdit = () => {
    let arrayList = checkeds.join().split(",");
    const newArr = arrayList.map((val, idx) => {
      return { theme: val };
    });

    if (newArr.length >= 5) {
      newArr.length = 5;
      console.log("newArr 자르기 후", newArr);
    }

    let token = sessionStorage.getItem("token");
    axios
      .put(
        "/aamurest/users/updatetheme",
        {
          id: sessionStorage.getItem("username"),
          themes: newArr,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((resp) => {
        console.log("테마 수정 성공 :", resp.data);
        alert("테마가 저장됬어요!");
      })
      .catch((error) => {
        console.log("테마 수정 실패 :", error);
      });
  };
  return (
    <>
      <div className="myTheme-List">
        <div className="myTheme-List-overlay">
          <CheckBoxBody>
            {themeItem.map((val, i) => {
              return (
                <CheckBox
                  key={val.THEMEID}
                  onClick={(e) => {
                    e.stopPropagation();
                    if (doCheck(e)) {
                      getCheckedBoxes(
                        e.target.parentElement.nextSibling.lastChild.checked,
                        e.target.parentElement.nextSibling.lastChild.value
                      );
                    }
                  }}
                >
                  <ImgCon>
                    <img
                      src={val.themeimg}
                      style={{
                        width: "100%",
                        height: "100%",
                        objectFit: "cover",
                        borderRadius: "5px",
                      }}
                    />
                  </ImgCon>
                  <div
                    style={{
                      textAlign: "center",
                      margin: "2px 0",
                      fontSize: "14px",
                    }}
                  >
                    <label for={val.THEMENAME}>{val.THEMENAME}</label>
                    <input
                      type="checkbox"
                      id={val.THEMENAME}
                      value={val.THEMENAME}
                      onChange={(e) => {
                        e.stopPropagation();
                        // getCheckedBoxes(e.target.checked, e.target.value);
                      }}
                      checked={checkeds.includes(val.THEMENAME) ? true : false}
                      hidden
                    />
                  </div>
                </CheckBox>
              );
            })}
          </CheckBoxBody>
          <div style={{ display: "flex", flexDirection: "row", gap: "5px" }}>
            <div
              className="myBox-List-back"
              onClick={(e) => {
                e.stopPropagation();
                // themeEdit();
                // setCheckeds(curr => [...curr,])
                setIsOpen(false);
              }}
            >
              저장
            </div>
            <div
              className="myBox-List-back"
              onClick={(e) => {
                e.stopPropagation();
                setCheckeds([]);
                setIsOpen(false);
              }}
            >
              취소
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

const CheckBoxBody = styled.div`
  position: relative;
  width: 100%;
  height: 500px;
  //   display: flex;
  //   flex-direction: column;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 5px;
  overflow-y: auto;
  &::-webkit-scrollbar {
    display: none;
  }
  z-index: 400;
`;
const CheckBox = styled.div`
  //   border: 1px solid rgb(128, 128, 128);
  box-shadow: var(--lightShadow);
  border-radius: 5px;
  width: 100%;
  height: 190px;
  padding: 5px;
  transition: 0.1s;
  border: 2px solid white;
  font-weight: bold;
  &:hover {
    border: 2px solid var(--skyblue);
  }
`;
const ImgCon = styled.div`
  //   border: 1px solid red;
  width: 100%;
  height: 90%;
`;

export default MyTheme;
