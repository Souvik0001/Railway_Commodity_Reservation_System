const https = require('https');
const fs = require('fs');
const path = require('path');

const resources = [
    {
        url: 'https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css',
        dest: 'css/font-awesome.css'
    },
    {
        url: 'https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.5.7/jquery.fancybox.min.css',
        dest: 'css/jquery.fancybox.min.css'
    },
    {
        url: 'https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.5.7/jquery.fancybox.min.js',
        dest: 'js/jquery.fancybox.min.js'
    },
    {
        url: 'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js',
        dest: 'js/jquery.min.js'
    },
    {
        url: 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css',
        dest: 'css/bootstrap.min.css'
    },
    {
        url: 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js',
        dest: 'js/bootstrap.bundle.min.js'
    },
    {
        url: 'https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css',
        dest: 'css/jquery.mCustomScrollbar.min.css'
    },
    {
        url: 'https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js',
        dest: 'js/jquery.mCustomScrollbar.concat.min.js'
    },
    {
        url: 'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js',
        dest: 'js/jquery.min.js'
    }
];

resources.forEach(resource => {
    const dir = path.dirname(resource.dest);
    if (!fs.existsSync(dir)) {
        fs.mkdirSync(dir, { recursive: true });
    }
    
    const file = fs.createWriteStream(resource.dest);
    https.get(resource.url, response => {
        response.pipe(file);
        file.on('finish', () => {
            file.close();
            console.log(`Downloaded: ${resource.dest}`);
        });
    }).on('error', (err) => {
        fs.unlink(resource.dest);
        console.error(`Error downloading ${resource.url}: ${err.message}`);
    });
});