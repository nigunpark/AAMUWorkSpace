import React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import axios from "axios";

const Login = () => {
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
    });
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            로그인
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="아이디를 입력하세요"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="비밀번호를 입력하세요"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="아이디 저장"
            />
            <Button
              // type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              onClick={loginPost}
            >
              로그인
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2">
                  비밀번호를 잊어버리셨나요?
                </Link>
              </Grid>
              <Grid item>
                <Link href="#" variant="body2">
                  {"회원가입"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  );
};

function loginPost() {
  axios
    .post("http://192.168.0.19:8080/aamurest/authenticate", {
      username: "ADMIN",
      password: "1234",
    })
    .then((resp) => {
      console.log(resp.data);
      sessionStorage.setItem('token',resp.data.token)
    })
    .catch((error) => {
      console.log(error);
    })
    .then(() => {
      console.log("로그인요청실행됨");
    });
}

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="https://localhost:3000/">
        AAMU
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

// function loginTest() {
//   axios
//     .post("/api/aamurest/authenticate", {
//       "username": "KIM",
//       "password": "1234"
//     })
//     .then((resp) => {
//       console.log("post 성공");
//       console.log(resp.data);
//       sessionStorage.setItem('token',resp.data.token)

//     })
//     .catch((error) => {
//       console.log("error : ", error);
//     })
//     .then(() => {
//       console.log("로그인요청됨");
//     });
// }
// function helloTest() {
//   axios
//     .get("/api/aamurest/Hello", {headers: {"Authorization" : `Bearer ${sessionStorage.getItem('token')}`}
//     })
//     .then((resp) => {
//       console.log("get 성공");
//       console.log(resp.data);
//       // sessionStorage.setItem('token',resp.data.token
//     })
//     .catch((error) => {
//       console.log("error : ", error);
//     })
//     .then(() => {
//       console.log("hello요청됨");
//     });
// }

const theme = createTheme();
export default Login;
