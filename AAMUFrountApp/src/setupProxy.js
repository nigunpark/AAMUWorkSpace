const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = (app) => {
  app.use(
    "/api",
    createProxyMiddleware({
      // target: "http://192.168.0.15:8080",//승훈
      target: "http://192.168.0.19:8080", //우재
      pathRewrite: {
        "^/api": "",
      },
      changeOrigin: true,
    })
  );
};
