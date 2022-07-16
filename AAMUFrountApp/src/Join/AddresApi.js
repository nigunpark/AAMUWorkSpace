import React from "react";
import DaumPostcode from "react-daum-postcode";
const AddresApi = ({ isOpenPost, setIsOpenPost }) => {
  const onCompletePost = (data) => {
    let fullAddr = data.address;
    let extraAddr = "";

    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddr += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddr +=
          extraAddr !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddr += extraAddr !== "" ? ` (${extraAddr})` : "";
    }

    console.log(data);
    console.log(fullAddr);
    setIsOpenPost(false);
  };
  const postCodeStyle = {
    display: "block",
    position: "absolute",
    top: "0%",
    width: "400px",
    height: "500px",
    borderRadius: "5px",
    border: "2px solid grey",
  };
  return (
    <div>
      <DaumPostcode
        style={postCodeStyle}
        autoClose
        onComplete={onCompletePost}
      />
    </div>
  );
};

export default AddresApi;
