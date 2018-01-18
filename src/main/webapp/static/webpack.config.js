const webpack = require('webpack');
var path = require('path');
var EncodingPlugin = require('webpack-encoding-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
module.exports = {
    devtool: 'source-map',
    entry: './app/boot.ts',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'build')
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                loaders: ['awesome-typescript-loader'],
                exclude: [/node_modules\/(?!(ng2-.+|ngx-.+))/]
            },
            { test: /\.(html)$/, loader: 'raw-loader' },
            {
                test: /\.css$/,
                loaders: ['to-string-loader', 'css-loader']
            }
        ]
    },
    resolve: {
        extensions: [".tsx", ".ts", ".js"]
    },
    plugins: [
        new webpack.ContextReplacementPlugin(/\@angular(\\|\/)core(\\|\/)esm5/, path.join(__dirname, './app')),
        new EncodingPlugin({encoding: 'utf-8'}),
        new CopyWebpackPlugin([
            { from: './node_modules/core-js/client/shim.min.js', to: 'libs' },
            { from: './node_modules/zone.js/dist/zone.js', to: 'libs' },
            { from: './node_modules/bootstrap/dist/css/bootstrap.min.css', to: 'css/bootstrap' },
            { from: './node_modules/ng2-toasty/bundles/style-bootstrap.css', to: 'css/ng2-toasty' },
            { from: './node_modules/ckeditor', to: 'libs/ckeditor' }
        ])
    ]
};
