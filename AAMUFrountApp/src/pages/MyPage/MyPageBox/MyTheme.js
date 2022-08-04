import React, { useState } from "react";

const MyTheme = () => {
  const themeItem = [
    {
      themeId: 1,
      themeName: "봄",
    },
    {
      themeId: 2,
      themeName: "여름",
    },
    {
      themeId: 3,
      themeName: "가을",
    },
    {
      themeId: 4,
      themeName: "겨울",
    },
    {
      themeId: 5,
      themeName: "산/트레킹",
    },
    {
      themeId: 6,
      themeName: "바다/해수욕장",
    },
    {
      themeId: 7,
      themeName: "호캉스",
    },
    {
      themeId: 8,
      themeName: "자전거",
    },
    {
      themeId: 9,
      themeName: "섬",
    },
    {
      themeId: 10,
      themeName: "맛집",
    },
    {
      themeId: 11,
      themeName: "힐링",
    },
    {
      themeId: 12,
      themeName: "드라이브",
    },
  ];

  const [theme, setTheme] = useState([]);
  const [isOpen, setIsOpen] = useState(false);

  const selectTheme = () => {};

  const onClickModal = () => {
    setIsOpen(true);
  };

  return (
    <div className="projects-theme">
      {theme.map((val, idx) => {
        return (
          <div key={idx} className="projects-theme-item">
            {val}
          </div>
        );
      })}
      <div className="projects-theme-item">봄</div>
      <div className="projects-theme-item">봄</div>

      <div style={{ position: "relative" }}>
        <svg
          className="link-icon feather feather-settings"
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          fill="none"
          stroke="currentColor"
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth="2"
          viewBox="0 0 24 24"
          style={{ cursor: "pointer" }}
          onClick={onClickModal}
        >
          <defs />
          <circle cx="12" cy="12" r="3" />
          <path d="M19.4 15a1.65 1.65 0 00.33 1.82l.06.06a2 2 0 010 2.83 2 2 0 01-2.83 0l-.06-.06a1.65 1.65 0 00-1.82-.33 1.65 1.65 0 00-1 1.51V21a2 2 0 01-2 2 2 2 0 01-2-2v-.09A1.65 1.65 0 009 19.4a1.65 1.65 0 00-1.82.33l-.06.06a2 2 0 01-2.83 0 2 2 0 010-2.83l.06-.06a1.65 1.65 0 00.33-1.82 1.65 1.65 0 00-1.51-1H3a2 2 0 01-2-2 2 2 0 012-2h.09A1.65 1.65 0 004.6 9a1.65 1.65 0 00-.33-1.82l-.06-.06a2 2 0 010-2.83 2 2 0 012.83 0l.06.06a1.65 1.65 0 001.82.33H9a1.65 1.65 0 001-1.51V3a2 2 0 012-2 2 2 0 012 2v.09a1.65 1.65 0 001 1.51 1.65 1.65 0 001.82-.33l.06-.06a2 2 0 012.83 0 2 2 0 010 2.83l-.06.06a1.65 1.65 0 00-.33 1.82V9a1.65 1.65 0 001.51 1H21a2 2 0 012 2 2 2 0 01-2 2h-.09a1.65 1.65 0 00-1.51 1z" />
        </svg>
        {isOpen == true ? (
          <div className="theme-modal">
            <div className="theme-modal-overlay">
              {themeItem.map((val, idx) => {
                return (
                  <div
                    className="theme-modal-select"
                    onClick={(e) => {
                      setIsOpen(false);
                    }}
                  >
                    {val.themeName}
                  </div>
                );
              })}
            </div>
          </div>
        ) : null}
      </div>
    </div>
  );
};

export default MyTheme;
