# Project Reflection

## Approach

Our overall approach to tackling this project began with drawing an architecture diagram based on the setting we envisioned and the map we constructed. This diagram served as the blueprint for our coding. During the coding phase, we identified areas where the code structure did not work as expected, prompting us to revise both the code and the architecture diagram. Our initial focus was to construct a large framework, which we then refined by adding details.

## Key Learnings

In completing this project, we learned several new things. We discovered how to create a grid map and generate items on the map without making every item an object, which proved to be much more efficient. Additionally, we learned how to enable the player to move within the grid, interact with NPCs, and write the game loop. We also learned how to debug step by step when the code structure is complicated.

## Implementation Reflections

Reflecting on our implementation, we realized it might have been better to write all four areas outside of the grid as separate classes. Each class could contain methods that we would call within the game loop, reducing the amount of code within the loop itself. This realization came after encountering issues with multiple scanners, leading us to conclude that there should only be one scanner in the game loop.

## Additional Features with Unlimited Time

If we had unlimited time, we would implement additional features such as enabling the player to gain the power of flight after surviving the waterfall, which was part of our original concept but not executed as planned. We would also add more interactive NPCs on the grid for greater engagement, allowing players to complete specific tasks to earn items. For instance, a player could help a raccoon find its way home in exchange for an apple, extending the functionality of the existing squirrel code.

## Feedback and Its Impact

The most helpful feedback we received came from two sources. Office hours with Professor Jordan inspired us to create an interactive map, set up acorns randomly, and enable the map to regenerate acorns after collection. Additionally, during the evening tutoring session before demo day, the tutor provided invaluable advice on resolving scanner-related issues. Originally, we had multiple scanners in both the player class and the game loop class, which caused problems in linking the code. The tutor suggested using a single scanner in the game loop and calling methods from the player class, which streamlined the process and resolved the issue.

## Advice to Our Past Selves

If we could give our past selves advice, we would emphasize the importance of planning how the player interacts with the code from the start. Our initial code structure became messy due to changes prompted by scanner issues, despite our efforts to follow the architecture diagram. Furthermore, we overcomplicated the game by aiming to create something akin to *Zork*. A simpler, more focused design would have been more realistic and achievable given our abilities.

## Team Dynamics

As for team dynamics, our experience had its highs and lows. The most productive phase was when we initially worked together to write the code. We were highly motivated, considering various challenges and writing substantial portions of the code as though we were constructing a new world. However, when we encountered bugs and difficulties, our productivity slowed, and we tended to procrastinate, hoping the other person would fix the issues. Despite this, on the night before demo day, the tutoring session reignited our motivation. With clear guidance on how to fix the problem, we regained our sense of purpose and became highly productive once more.

# Design Justification

## Current Design
Backpack Management with Hashtable: The player's inventory (backpack) is implemented using a Hashtable to store items and their quantities. This allows efficient lookups for item presence and count, enabling quick checks when adding, dropping, or examining items.

## Alternative
Using an Object-Oriented Backpack Class
Instead of directly using a Hashtable for the player's inventory, a dedicated Backpack class could encapsulate inventory management. This class would manage item storage, enforce capacity limits, and handle item-specific rules (e.g., multiple acorns or single-item constraints).

## Why Against
Introducing a new class for a relatively simple inventory system adds unnecessary complexity for the current scope of the game.
Managing an additional class could make the codebase harder to follow for a beginner audience or small team.