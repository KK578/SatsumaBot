package uk.co.thelittlemandarin.views;

import com.slack.api.model.ModelConfigurator;
import com.slack.api.model.view.View;
import lombok.Builder;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

@Builder
public class HomeViewConfigurer implements ModelConfigurator<View.ViewBuilder> {

    private final int count;

    @Override
    public View.ViewBuilder configure(View.ViewBuilder viewBuilder) {
        return viewBuilder.type("home")
            .blocks(asBlocks(
                section(section -> section.text(markdownText(mt -> mt.text("*Welcome to your _App's Home_* :tada:")))),
                divider(),
                section(section -> section.text(markdownText(mt -> mt.text("This button won't do much for now but you can set up a listener for it using the `actions()` method and passing its unique `action_id`. See an example on <https://slack.dev/java-slack-sdk/guides/interactive-components|slack.dev/java-slack-sdk>.")))),
                actions(actions -> actions
                    .elements(asElements(
                        button(b -> b.text(plainText(pt -> pt.text("Click me!"))).actionId("buttonCounter"))
                    ))
                ),
                section(s -> s.text(markdownText(String.format("Wow the count is now %s.", count))))
            ));
    }

}
