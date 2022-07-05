import { configureStore, createSlice } from "@reduxjs/toolkit";

let localNameForMarker = createSlice({
  name: "localNameForMarker",
  initialState: "",
  reducers: {
    changeLnfM(state, action) {
      return action.payload;
    },
  },
});
export let { changeLnfM } = localNameForMarker.actions;

let kindOfInfo = createSlice({
  name: "kindOfInfo",
  initialState: "추천장소",
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
  initialState: [],
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
    addAtimeArrForJangso(state, action) {
      state.atime = action.payload;
    },
  },
});
export let {
  changeArrForJangso,
  addArrInForJangso,
  deleteArrInJangso,
  addAtimeArrForJangso,
} = arrForJangso.actions;

//일정등록버튼 누르고 우재한테 받은 데이터를 저장하는 state
let fromWooJaeData = createSlice({
  name: "wooJaeData",
  initialState: [],
});

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
  initialState: [],
  reducers: {
    changeArrForSukso(state, action) {
      return action.payload;
    },
  },
});
export let { changeArrForSukso } = arrForSukso.actions;

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

//db에서 계산되서 받아온 이동시간이 저장되는 배열 state
let movingTime = createSlice({
  name: "movingTime",
  initialState: [
    { day: 1, start: 0, first: 10, second: 11, third: 12 },
    { day: 2, start: 0, first: 13, second: 14, third: 15 },
  ], // {day:1,start:0,first:10,second:35,third:22}...
  reducers: {
    addMovingTime(state, action) {
      state.push(action.payload);
    },
    corrMovingTime(state, action) {
      return;
    },
  },
});
export let { addMovingTime } = movingTime.actions;

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
    movingTime: movingTime.reducer,
  },
  //planTripTime.js 168번줄쯤의 fullDate를 위한 serializable무시
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});
