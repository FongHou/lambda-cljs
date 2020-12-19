module.exports = {
  target:  'node',
  devtool: 'inline-source-map',
  entry: './out/lambda.js',
  output: {
    libraryTarget: 'commonjs',
    filename: 'index.js',
    path: __dirname + "/out",
  }
}
