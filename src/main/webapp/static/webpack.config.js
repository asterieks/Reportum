const webpack = require('webpack');
var path = require('path');
var EncodingPlugin = require('webpack-encoding-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
module.exports = {
    devtool: 'source-map',
    entry: './app/boot.ts',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist')
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
            { from: './node_modules/bootstrap/dist/fonts', to: 'css/fonts' },
            { from: './node_modules/ng2-toasty/bundles/style-bootstrap.css', to: 'css/ng2-toasty' },
            { from: './node_modules/ckeditor', to: 'libs/ckeditor' },
            { from: './node_modules/normalize.css/normalize.css', to: 'css/necolas' },
            { from: './node_modules/typeface-raleway/files/raleway-latin-100.woff2', to: "fonts/raleway" },
            { from: './node_modules/typeface-raleway/files/raleway-latin-200.woff2', to: "fonts/raleway" },
            { from: './node_modules/typeface-raleway/files/raleway-latin-300.woff2', to: "fonts/raleway" },
            { from: './node_modules/typeface-raleway/files/raleway-latin-400.woff2', to: "fonts/raleway" },
            { from: './node_modules/typeface-raleway/files/raleway-latin-600.woff2', to: "fonts/raleway" },
            { from: './node_modules/typeface-raleway/files/raleway-latin-700.woff2', to: "fonts/raleway" },
            { from: './node_modules/roboto-fontface/fonts/roboto/Roboto-Regular.woff2', to: "fonts/roboto" },
            { from: './node_modules/typeface-source-code-pro/files', to: "fonts/source-code-pro" },
            { from: './assets/images', to: "images" }
        ])
    ]
};
