const webpack = require('webpack');
var path = require('path');
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
    ]
};
