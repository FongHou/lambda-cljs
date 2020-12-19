module.exports = {
  target: 'node',
  entry: './out/lambda.js',
  output: {
    libraryTarget: 'umd',
    filename: 'index.js',
    path: __dirname + "/out",
  }
}
