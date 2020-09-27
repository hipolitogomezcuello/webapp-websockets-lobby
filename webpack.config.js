module.exports = {
  entry: ['babel-polyfill', 'whatwg-fetch', 'promise-polyfill', './src/main/js/App.js'],
  output: {
    path: __dirname,
    filename: './target/classes/static/built/bundle.js'
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: 'babel-loader'
      },
      {
        test: /\.css$/,
        use: [ 'style-loader', 'css-loader' ]
      }
    ]
  },
  performance: {
    hints: false
  }
}
