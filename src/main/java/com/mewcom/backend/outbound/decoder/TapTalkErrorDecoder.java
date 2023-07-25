package com.mewcom.backend.outbound.decoder;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;

public class TapTalkErrorDecoder implements ErrorDecoder {

  final Decoder decoder;
  final ErrorDecoder errorDecoder = new ErrorDecoder.Default();

  public TapTalkErrorDecoder(Decoder decoder) {
    this.decoder = decoder;
  }

  @Override
  public Exception decode(String s, Response response) {
    return errorDecoder.decode(s, response);
  }
}
