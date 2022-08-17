[아무여행] 안드로이드 프로젝트 입니다
======================

담당 박승훈

## 💻사용기술 및 라이브러리
<div>
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/jetpackcompose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white"/>
<img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=Android&logoColor=white"/>
<img src="https://img.shields.io/badge/AndroidStudio-3DDC84?style=flat-square&logo=AndroidStudio&logoColor=white"/>
<img src="https://img.shields.io/badge/Firebase-FFCA28?style=flat-square&logo=Firebase&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
<img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white"/>
<img src="https://img.shields.io/badge/JSON-000000?style=flat-square&logo=JSON&logoColor=white"/>
<img src="https://img.shields.io/badge/JSONWebTokens-000000?style=flat-square&logo=JSONWebTokens&logoColor=white"/>
<img src="https://img.shields.io/badge/FontAwesome-528DD7?style=flat-square&logo=FontAwesome&logoColor=white"/>
</div>

## 그밖에 사용기술 및 라이브러리
[**lifecycle**](https://developer.android.com/jetpack/androidx/releases/lifecycle?hl=ko) / 
[**livedata**](https://developer.android.com/topic/libraries/architecture/livedata?hl=ko) / 
[**viewmodel**](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ko) / 
[**data-binding**](https://developer.android.com/topic/libraries/data-binding?hl=ko) / 
[**navigation**](https://developer.android.com/jetpack/androidx/releases/navigation?hl=ko) / 
[**paging**](https://developer.android.com/jetpack/androidx/releases/paging) / 
[**kakaomapapi**](https://apis.map.kakao.com/android/documentation/) / 
[**lottiefiles**](https://lottiefiles.com/) / 
[**okhttp3**](https://square.github.io/okhttp/) / 
[**retrofit2**](https://square.github.io/retrofit/) / 
[**coroutines**](https://developer.android.com/kotlin/coroutines) / 
[**coil**](https://coil-kt.github.io/coil/compose/) /
[**jackson**](https://github.com/FasterXML/jackson) / 
[**exoplayer**](https://github.com/google/ExoPlayer) / 
[**permissions**](https://google.github.io/accompanist/permissions/)
<hr/>

## 주요 기술 소스코드
[**viewmodel**](https://github.com/nigunpark/AAMUWorkSpace/blob/main/AAMUAndroidApp/app/src/main/java/com/aamu/aamuandroidapp/components/chatlist/AAMUChatViewModel.kt)

    class AAMUChatViewModelFactory(val context: Context) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AAMUChatViewModel(context) as T
        }
    }

    class AAMUChatViewModel(context: Context) : ViewModel(){
        private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

        private val context = context

        val chatingRoomList = MutableLiveData<List<AAMUChatRoomResponse>>(emptyList())
        val errorLiveData = MutableLiveData<String>()

        fun getChatingList(userid : String){
            viewModelScope.launch {
                aamuRepository.getChatRoomList(userid)
                    .collect{ chatinglist->
                        if(chatinglist.isNotEmpty()){
                            chatingRoomList.value = chatinglist
                        }
                        else{
                            errorLiveData.value = "아직 사용자와 채팅한 내역이 없어요"
                        }
                    }
            }

        }
    }
    
[**retrofit2 / okhttp3**](https://github.com/nigunpark/AAMUWorkSpace/blob/main/AAMUAndroidApp/app/src/main/java/com/aamu/aamuandroidapp/data/api/AAMUApi.kt)

        operator fun invoke():AAMUApi{
            val requestInterceptor  = Interceptor{ chain ->

                        val request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "Bearer " + (getToken() ?: ""))
                            .build()
                    try {
                        return@Interceptor chain.proceed(request)
                    }
                    catch (e : Exception){
                        val response : okhttp3.Response = okhttp3.Response.Builder()
                            .code(1)
                            .request(request)
                            .protocol(Protocol.HTTP_2)
                            .message("서버나 인터넷이 연결인 안됬어요!!")
                            .build()
                        return@Interceptor response
                    }
                }



            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
                .build()
                .create(AAMUApi::class.java)
        }

[**coroutines**](https://github.com/nigunpark/AAMUWorkSpace/blob/main/AAMUAndroidApp/app/src/main/java/com/aamu/aamuandroidapp/data/api/repositories/AAMURepositoryImpl.kt)

    override suspend fun getPlaceOne(
        contentid: Int,
        contenttypeid: Int
    ): Flow<AAMUPlaceResponse> = flow<AAMUPlaceResponse> {
        val response = aamuApi.getPlaceOne(contentid,contenttypeid)
        if(response.isSuccessful){
            emit(response.body() ?: AAMUPlaceResponse())
        }
        else
            emit(AAMUPlaceResponse())
    }.catch {
        emit(AAMUPlaceResponse())
    }.flowOn(Dispatchers.IO)
    
[**coil**](https://github.com/nigunpark/AAMUWorkSpace/blob/main/AAMUAndroidApp/app/src/main/java/com/aamu/aamuandroidapp/components/gram/posts/PostItem.kt)

      Image(
          painter = rememberAsyncImagePainter(
              model = ImageRequest.Builder(LocalContext.current)
                  .data(imageId ?: R.drawable.no_image)
                  .crossfade(true)
                  .build(),
              contentScale = ContentScale.Crop
          ),
          modifier = modifier.fillMaxWidth().height(450.dp),
          contentDescription = contentDescription,
          contentScale = ContentScale.Crop
      )
    
[**paging**](https://github.com/nigunpark/AAMUWorkSpace/blob/main/AAMUAndroidApp/app/src/main/java/com/aamu/aamuandroidapp/components/routebbs/detail/RouteBBSDetail.kt)

        HorizontalPager(state = pagerState) { index ->
            val painterOther = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        bbsDetail!!.photo?.getOrNull(index) ?: R.drawable.no_image
                    )
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop
            )

[**permissions**]()

    val permissionState = rememberMultiplePermissionsState(permissions = permissions)
    val isPermissionDenied = remember {
        mutableStateOf(false)
    }
    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = {
            Scaffold(
                Modifier
                    .background(aamuorange.copy(alpha = 0.1f))
                    .fillMaxSize()
                    .padding(PluckDimens.One),
            ) {
                isPermissionDenied.value = false
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(aamuorange.copy(alpha = 0.1f))
                        .padding(PluckDimens.Six)
                        ,
                    horizontalAlignment = Alignment.CenterHorizontally
                )

[**kakaomapapi**](https://github.com/nigunpark/AAMUWorkSpace/blob/main/AAMUAndroidApp/app/src/main/java/com/aamu/aamuandroidapp/components/aamuplan/AAMUPlanViewModel.kt)

        fun setPlanDetailCenter(place : AAMUPlaceResponse){
            mapView.removeAllPOIItems()
            mapView.removeAllPolylines()
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(place.mapy!! - 0.05,place.mapx!!),true)
            val mCustomMarker :MapPOIItem =MapPOIItem()
            mCustomMarker.itemName = place.title
            mCustomMarker.mapPoint = MapPoint.mapPointWithGeoCoord(place.mapy!!,place.mapx!!)
            mCustomMarker.markerType =MapPOIItem.MarkerType.CustomImage

            mCustomMarker.customImageResourceId = R.drawable.map_end_icon
            mCustomMarker.setCustomImageAutoscale(false)

            mapView.addPOIItem(mCustomMarker)
            mapView.setZoomLevel(5, true)
        }

[**navigation**](https://github.com/nigunpark/AAMUWorkSpace/tree/main/AAMUAndroidApp/app/src/main/res/navigation)

![image](https://user-images.githubusercontent.com/107670168/185070770-bfd95ab7-40ee-439d-a006-331d41738181.png)



