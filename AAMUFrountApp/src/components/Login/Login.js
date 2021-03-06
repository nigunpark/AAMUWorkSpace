import React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { KAKAO_AUTH_URL } from "./Kakao/OAuth";

const Login = () => {
  let navigate = useNavigate();
  const theme = createTheme();
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    axios
      .post("/aamurest/authenticate", {
        username: data.get("email"),
        password: data.get("password"),
      })
      .then((resp) => {
        sessionStorage.setItem("token", resp.data.token);
        navigate("/");
      })
      .catch((error) => {
        console.log("error:", error);
      })
      .then(() => {
        console.log("로그인 post요청함");
      });
  };
  return (
    <div>
      <ThemeProvider theme={theme} sx={{ marginTop: 8 }}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <Box
            sx={{
              marginTop: 11,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Avatar sx={{ m: 1, bgcolor: "warning.light" }}>
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
                color="warning"
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
                color="warning"
                margin="normal"
                required
                fullWidth
                name="password"
                label="비밀번호를 입력하세요"
                type="password"
                id="password"
                autoComplete="current-password"
              />
              {/* <FormControlLabel
              control={<Checkbox value="remember" color="warning" />}
              label="아이디 저장"
            /> */}
              <Button
                color="warning"
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                로그인
              </Button>
              <Button
                // type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
                // onClick={loginPost}
                href={KAKAO_AUTH_URL}
              >
                <img src="/images/kakaoLogin.png" />
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
    </div>
  );
};

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

export default Login;
