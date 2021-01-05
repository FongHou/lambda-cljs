module.exports = {
  target:  'node',
  mode: 'production',
  entry: './out/lambda.js',
  output: {
    libraryTarget: 'commonjs',
    filename: 'index.js',
    path: __dirname + "/out",
  },
  devtool: 'source-map',
  module: {
    rules: [
      {
        test: /\.js$/,
        enforce: "pre",
        use: ["source-map-loader"],
      }
    ]
  }
}
