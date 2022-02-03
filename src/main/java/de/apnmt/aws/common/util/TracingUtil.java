package de.apnmt.aws.common.util;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.TraceHeader;

import java.util.Optional;

public class TracingUtil {

    public static void beginTracing(String name, String traceId, boolean enabled) {
        if (enabled) {
            Segment segment = AWSXRay.beginSegment(name);
            TraceHeader traceHeader = TraceHeader.fromString(traceId);
            // Recover the trace context from the trace header
            segment.setTraceId(traceHeader.getRootTraceId());
            segment.setParentId(traceHeader.getParentId());
            segment.setSampled(traceHeader.getSampled().equals(TraceHeader.SampleDecision.SAMPLED));
        }
    }

    public static void addException(Exception e, boolean enabled) {
        if (enabled) {
            Optional<Segment> opt = AWSXRay.getCurrentSegmentOptional();
            if (opt.isPresent()) {
                Segment segment = opt.get();
                segment.addException(e);
            }
        }
    }

    public static void endTracing(boolean enabled) {
        if (enabled) {
            AWSXRay.endSegment();
        }
    }

    public static String createTraceId(boolean enabled) {
        if (enabled) {
            Segment segment = AWSXRay.getCurrentSegment();
            return "Root=" + segment.getTraceId() + ";Parent=" + segment.getParentId() + ";Sampled=1";
        }
        return "";
    }

}
