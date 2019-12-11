var path    = require('path');
var hwp     = require('html-webpack-plugin');

module.exports = {
    entry: path.join(__dirname, '/index.js'),
    output: {
        filename: 'build.js',
        path: path.join(__dirname, '/dist')
    },
    module:{
        rules:[{
            exclude: /node_modules/,
            test: /\.js$/,
            loader: 'babel-loader',
            query: {
              presets: ['env','react']
            }
        }]
    },
    plugins:[
        new hwp({template:path.join(__dirname, '/src/index.html')})
    ]
}