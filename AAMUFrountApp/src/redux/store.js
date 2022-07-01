import { configureStore, createSlice } from "@reduxjs/toolkit";

let localNameForMarker = createSlice({
  name: "localNameForMarker",
  initialState: null,
  reducers: {
    changeLnfM(state, action) {
      return action.payload;
    },
  },
});
export let { changeLnfM } = localNameForMarker.actions;

let kindOfInfo = createSlice({
  name: "kindOfInfo",
  initialState: "장소",
  reducers: {
    changeInfo(state, action) {
      return action.payload;
    },
  },
});

export let { changeInfo } = kindOfInfo.actions;
//우측 추천장소 배열
let arrForJangso = createSlice({
  name: "arrForJangso",
  initialState: [
    {
      areacode: 1,
      sigungucode: 11,
      contentid: 126500,
      contenttypeid: 12,
      mapx: 127.0406812854,
      mapy: 37.5924523515,
      title: "홍릉수목원",
      addr: "서울특별시 동대문구 회기로 57",
      image: "http://tong.visitkorea.or.kr/cms/resource/80/746580_image2_1.jpg",
      image2:
        "http://tong.visitkorea.or.kr/cms/resource/80/746580_image3_1.jpg",
      playtime:
        "평일(화~금) 숲해설 10:30, 13:30, 15:30<br /><br />\n주말_ 자유관람<br />\n하절기 09:00~18:00<br />\n동절기 09:00~17:00<br /><br />\n주말_숲해설 <br />\n3월~11월 10:30, 14:00",
      tel: "02-961-2522",
      resttime: "매주 월요일, 5월 1일 및 모든 법정 공휴일 (일요일 제외)",
      checkintime: null,
      checkouttime: null,
      url: null,
      kakaokey: "443387508",
      menu: null,
    },
    {
      areacode: 1,
      sigungucode: 15,
      contentid: 125452,
      contenttypeid: 12,
      mapx: 127.0543676446,
      mapy: 37.4416867721,
      title: "청계산",
      addr: "서울특별시 서초구 원터길",
      image:
        "http://tong.visitkorea.or.kr/cms/resource/41/2023841_image2_1.jpg",
      image2:
        "http://tong.visitkorea.or.kr/cms/resource/41/2023841_image3_1.jpg",
      playtime: "",
      tel: "공원녹지과  02-2155-6870",
      resttime: null,
      checkintime: null,
      checkouttime: null,
      url: null,
      kakaokey: "10847903",
      menu: null,
    },
    {
      areacode: 1,
      sigungucode: 23,
      contentid: 126537,
      contenttypeid: 12,
      mapx: 126.9864254967,
      mapy: 37.5791612548,
      title: "북촌한옥마을",
      addr: "서울특별시 종로구 계동길 37",
      image:
        "http://tong.visitkorea.or.kr/cms/resource/06/2512006_image2_1.jpg",
      image2:
        "http://tong.visitkorea.or.kr/cms/resource/06/2512006_image3_1.jpg",
      playtime:
        "* 허용시간 : 10:00 ~17:00(주중,토) / 일요일 : 골목길 쉬는 날<br />\n* 대상지역 : 북촌로 11길 일대 약 100m 구간",
      tel: "02-2148-4161",
      resttime: "(일요일) 골목길 쉬는 날",
      checkintime: null,
      checkouttime: null,
      url: null,
      kakaokey: "25027484",
      menu: null,
    },
    {
      areacode: 1,
      sigungucode: 24,
      contentid: 126747,
      contenttypeid: 12,
      mapx: 126.9940059289,
      mapy: 37.5591248302,
      title: "남산골한옥마을",
      addr: "서울특별시 중구 퇴계로34길 28",
      image:
        "http://tong.visitkorea.or.kr/cms/resource/62/1946562_image2_1.jpg",
      image2:
        "http://tong.visitkorea.or.kr/cms/resource/62/1946562_image3_1.jpg",
      playtime:
        "2021년 (1월~별도 공지시)<br />\n- 09:00 ~ 18:00 (코로나19 단축운영)<br />\n※ 매주 월요일 정기휴관",
      tel: "공연 02-2261-0500<br />\n교육/전시 02-2266-6928<br />\n전통혼례 02-2263-0854<br />\n대관/운영 02-2261-0513<br />\n전통체험 02-2266-6923",
      resttime: "월요일",
      checkintime: null,
      checkouttime: null,
      url: null,
      kakaokey: "8246127",
      menu: null,
    },
    {
      areacode: 1,
      sigungucode: 10,
      contentid: 126481,
      contenttypeid: 12,
      mapx: 127.0184192271,
      mapy: 37.6969870145,
      title: "도봉산",
      addr: "서울특별시 도봉구 도봉동",
      image:
        "http://tong.visitkorea.or.kr/cms/resource/65/1894465_image2_1.jpg",
      image2:
        "http://tong.visitkorea.or.kr/cms/resource/65/1894465_image3_1.jpg",
      playtime: "",
      tel: "02-954-2566",
      resttime: "연중무휴",
      checkintime: null,
      checkouttime: null,
      url: null,
      kakaokey: "18580958",
      menu: null,
    },
  ],
  reducers: {
    changeArrForJangso(state, action) {
      return action.payload;
    },
    //left에서 장소지웠을때 다시 right에 추가하기
    addArrInForJangso(state, action) {
      state = state.unshift(action.payload);
    },
    deleteArrInJangso(state, action) {
      state.splice(
        state.findIndex((local) => {
          return local.contentid === action.payload.contentid;
        }),
        1
      );
    },
  },
});
export let { changeArrForJangso, addArrInForJangso, deleteArrInJangso } =
  arrForJangso.actions;

//추천장소에서 선택한 장소를 넣는 배열
let arrForPickJangso = createSlice({
  name: "arrForPickJangso",
  initialState: [],
  reducers: {
    addPickJangso(state, action) {
      state = state.push(action.payload);
    },
    deletePickJangso(state, action) {
      return (state = state.filter(
        (local) => local.contentid !== action.payload.contentid
      ));
    },
    deleteAllPickJanso(state, action) {
      return action.payload;
    },
  },
});
export let { addPickJangso, deleteAllPickJanso, deletePickJangso } =
  arrForPickJangso.actions;

//추천숙소
let arrForSukso = createSlice({
  name: "arrForSukso",
  initialState: ["하얏트", "그랜드조선", "트럼프", "뜨밤호텔", "낮져밤이호텔"],
  reducers: {
    changeArrForSukso(state, action) {
      return action.payload;
    },
  },
});
export let { changeArrForSukso } = arrForJangso.actions;

//숙소와 장소모달중 어떤 것을 보여줄지
let showWhichModal = createSlice({
  name: "showWhichModal",
  initialState: false,
  reducers: {
    changeShowWhichModal(state, action) {
      return action.payload;
    },
  },
});
export let { changeShowWhichModal } = showWhichModal.actions;

//왼쪽 time state관리
let leftSideTimeSetter = createSlice({
  name: "leftSideTimeSetter",
  initialState: 0,
  reducers: {
    timeSetter(state, action) {
      return state + action.payload;
    },
    setInitForTime(state, action) {
      return action.payload;
    },
  },
});
export let { timeSetter, setInitForTime } = leftSideTimeSetter.actions;

//왼쪽 minutes state관리
let leftSideMinSetter = createSlice({
  name: "leftSideMinSetter",
  initialState: 0,
  reducers: {
    minSetter(state, action) {
      return state + action.payload;
    },
    setInitForMin(state, action) {
      return action.payload;
    },
  },
});
export let { minSetter, setInitForMin } = leftSideMinSetter.actions;

//여행기간과 추천숙소 넣는 배열
let saveDaysNPickedSuksoRedux = createSlice({
  name: "saveDaysNPickedSuksoRedux",
  initialState: [],
  reducers: {
    changeSaveDaysRedux(state, action) {
      return new Array(action.payload).fill(0);
    },
    changeArrSaveDaysRedux(state, action) {
      return action.payload;
    },
    addOneSaveDaysRedux(state, action) {
      state = state.splice(state.indexOf(0), 1, action.payload);
    },
    delAllSaveDaysRedux(state, action) {
      return action.payload;
    },
    delOneSaveDaysRedux(state, action) {
      state = state.splice(state.indexOf(action.payload), 1, 0);
    },
  },
});
export let {
  changeSaveDaysRedux,
  changeArrSaveDaysRedux,
  addOneSaveDaysRedux,
  delAllSaveDaysRedux,
  delOneSaveDaysRedux,
} = saveDaysNPickedSuksoRedux.actions;

//여행기간
let tripPeriod = createSlice({
  name: "tripPeriod",
  initialState: [0],
  reducers: {
    changeTripPeriod(state, action) {
      return new Array(action.payload).fill(0);
    },
  },
});
export let { changeTripPeriod } = tripPeriod.actions;

//이동수단 선택 state
let pickedTransport = createSlice({
  name: "pickedTransport",
  initialState: "personal",
  reducers: {
    changePickedTransport(state, action) {
      return action.payload;
    },
  },
});
export let { changePickedTransport } = pickedTransport.actions;

//메인페이지 leftSide 여행시간 상세설정 시 시작시간들 넣는 배열
let timeSetObj = createSlice({
  name: "timeSetObj",
  initialState: [],
  reducers: {
    changeTimeSetObj(state, action) {
      state.push(action.payload);
    },
    delTimeSetObj(state, action) {
      state.splice(
        state.findIndex((data) => {
          return data.day === action.payload;
        }),
        1
      );
    },
    corrTimeSetObj(state, action) {
      state[
        state.findIndex((obj) => {
          return obj.day === action.payload.day;
        })
      ] = action.payload;
    },
  },
});
export let { changeTimeSetObj, delTimeSetObj, corrTimeSetObj } =
  timeSetObj.actions;

//월과 일 저장
let monthNdate = createSlice({
  name: "monthNdate",
  initialState: [],
  reducers: {
    addMonthNDate(state, action) {
      state.push(action.payload);
    },
    resetMonthNDate(state, action) {
      return action.payload;
    },
  },
});
export let { addMonthNDate, resetMonthNDate } = monthNdate.actions;

export default configureStore({
  reducer: {
    localNameForMarker: localNameForMarker.reducer,
    kindOfInfo: kindOfInfo.reducer,
    arrForJangso: arrForJangso.reducer,
    arrForSukso: arrForSukso.reducer,
    arrForPickJangso: arrForPickJangso.reducer,
    leftSideTimeSetter: leftSideTimeSetter.reducer,
    leftSideMinSetter: leftSideMinSetter.reducer,
    // rightToggle: rightToggle.reducer,
    saveDaysNPickedSuksoRedux: saveDaysNPickedSuksoRedux.reducer,
    showWhichModal: showWhichModal.reducer,
    pickedTransport: pickedTransport.reducer,
    timeSetObj: timeSetObj.reducer,
    tripPeriod: tripPeriod.reducer,
    monthNdate: monthNdate.reducer,
  },
  //planTripTime.js 168번줄쯤의 fullDate를 위한 serializable무시
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});
