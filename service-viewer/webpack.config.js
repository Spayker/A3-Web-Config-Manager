var path    = require('path');
var hwp     = require('html-webpack-plugin');
var BUILD_DIR = path.resolve(__dirname, '');

module.exports = {
    entry: path.join(__dirname, '/src/index.jsx'),
    output: {
        filename: 'build.js',
        path: path.join(__dirname, '/dist')
    },
    devtool: 'eval-source-map',
    module:{
        rules:[
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        babelrc: false,
                        presets: [
                            ["@babel/env", {
                                "targets": {
                                    'browsers': ['Chrome >=59']
                                },
                                "modules": false,
                                "loose": true
                            }], "@babel/react"],

                        plugins: [
                            "@babel/proposal-object-rest-spread",
                            ["@babel/plugin-transform-classes"]
                        ]
                    }
                },
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    plugins:[
        new hwp({template:path.join(__dirname, '/src/index.html')})
    ]
}