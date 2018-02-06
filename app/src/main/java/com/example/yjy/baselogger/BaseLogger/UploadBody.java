package com.example.yjy.baselogger.BaseLogger;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * Created by yjy on 2018/2/4.
 */

public class UploadBody extends RequestBody {

    private RequestBody mRequestBody;
    private int currentByteCount = 0;

    public UploadBody(RequestBody mRequestBody){
        this.mRequestBody = mRequestBody;

    }


    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        ForwardingSink downloadSink = new ForwardingSink(sink) {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                currentByteCount += byteCount;
                super.write(source, byteCount);
            }
        };

        BufferedSink bufferedSink = Okio.buffer(downloadSink);
        mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }
}
