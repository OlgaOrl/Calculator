# Development Workflow & Best Practices

## Overview

This document outlines the comprehensive development methodology, Git workflow, and professional practices used in the Calculator project. It demonstrates industry-standard software development practices suitable for academic evaluation and professional environments.

## Table of Contents

1. [Git Workflow Strategy](#git-workflow-strategy)
2. [Branch Naming Conventions](#branch-naming-conventions)
3. [Commit Message Standards](#commit-message-standards)
4. [Code Review Process](#code-review-process)
5. [Development Environment Setup](#development-environment-setup)
6. [Testing Strategy](#testing-strategy)
7. [Deployment Process](#deployment-process)
8. [Contributing Guidelines](#contributing-guidelines)

## Git Workflow Strategy

### Why Clean Main Branch Matters

The **main branch must always remain clean and deployable** for several critical reasons:

1. **Production Readiness**: Main branch represents production-ready code
2. **Continuous Integration**: Automated builds and deployments rely on stable main
3. **Team Collaboration**: Provides stable base for all feature development
4. **Release Management**: Enables reliable versioning and hotfix deployment
5. **Academic Standards**: Demonstrates understanding of professional practices

### Feature Branch Workflow with Squash-Merge

Our workflow uses **feature branches with squash-merge** to maintain clean history:

```
main (always deployable)
├── feature/basic-arithmetic (squash-merged)
├── feature/memory-operations (squash-merged)
├── feature/gui-interface (squash-merged)
└── feature/advanced-functions (in development)
```

#### Benefits of Squash-Merge:
- **Clean History**: Each feature appears as single commit in main
- **Atomic Changes**: Complete features merged as cohesive units
- **Easy Rollback**: Simple to revert entire features if needed
- **Clear Timeline**: Linear history shows feature progression

### Complete Feature Development Process

#### Step 1: Start New Feature
```bash
# Ensure main is up to date
git checkout main
git pull origin main

# Create and switch to feature branch
git checkout -b feature/memory-operations

# Verify you're on the correct branch
git branch --show-current
# Output: feature/memory-operations
```

#### Step 2: Development Cycle
```bash
# Make changes to files
# Edit src/main/java/com/calculator/Calculator.java

# Stage and commit changes
git add src/main/java/com/calculator/Calculator.java
git commit -m "feat: implement memory store functionality"

# Add tests
git add src/test/java/com/calculator/CalculatorTest.java
git commit -m "test: add memory store unit tests"

# Update documentation
git add docs/ALGORITHM.md
git commit -m "docs: document memory operations algorithm"

# Continue development with multiple commits
git add .
git commit -m "refactor: extract memory validation logic"
git commit -m "fix: handle edge cases in memory operations"
```

#### Step 3: Prepare for Merge
```bash
# Ensure all tests pass
mvn clean test

# Update main and check for conflicts
git checkout main
git pull origin main
git checkout feature/memory-operations

# Rebase feature branch (optional but recommended)
git rebase main

# Push feature branch
git push origin feature/memory-operations
```

#### Step 4: Squash-Merge Process

**Via GitHub/GitLab (Recommended):**
1. Create Pull Request from `feature/memory-operations` to `main`
2. Select "Squash and merge" option
3. Edit squash commit message:
   ```
   feat: implement memory operations (#12)
   
   - Add memory store, recall, clear functionality
   - Implement memory arithmetic (M+, M-)
   - Add comprehensive unit tests
   - Update documentation with algorithms
   ```

**Via Command Line:**
```bash
# Switch to main branch
git checkout main
git pull origin main

# Squash-merge feature branch
git merge --squash feature/memory-operations

# Create single commit for entire feature
git commit -m "feat: implement memory operations

- Add memory store, recall, clear functionality
- Implement memory arithmetic (M+, M-)
- Add comprehensive unit tests
- Update documentation with algorithms"

# Push to main
git push origin main

# Clean up feature branch
git branch -d feature/memory-operations
git push origin --delete feature/memory-operations
```

## Branch Naming Conventions

### Standard Branch Prefixes

| Prefix | Purpose | Example | Description |
|--------|---------|---------|-------------|
| `feature/` | New functionality | `feature/advanced-math` | Adding new features |
| `bugfix/` | Bug fixes | `bugfix/division-by-zero` | Fixing existing issues |
| `hotfix/` | Critical production fixes | `hotfix/security-patch` | Urgent production fixes |
| `docs/` | Documentation only | `docs/api-documentation` | Documentation updates |
| `refactor/` | Code improvements | `refactor/calculator-class` | Code restructuring |
| `test/` | Test improvements | `test/increase-coverage` | Testing enhancements |

### Branch Naming Rules

1. **Use lowercase with hyphens**: `feature/memory-operations`
2. **Be descriptive but concise**: `bugfix/gui-button-alignment`
3. **Include issue number if applicable**: `feature/calculator-gui-#15`
4. **Avoid special characters**: No spaces, underscores, or symbols

### Examples of Good Branch Names
```bash
# Feature branches
feature/basic-arithmetic-operations
feature/gui-calculator-interface
feature/memory-functionality
feature/keyboard-shortcuts

# Bug fix branches
bugfix/division-by-zero-handling
bugfix/gui-display-overflow
bugfix/memory-clear-issue

# Hotfix branches
hotfix/critical-calculation-error
hotfix/security-input-validation

# Documentation branches
docs/uml-class-diagrams
docs/algorithm-documentation
docs/user-manual-update
```

## Commit Message Standards

### Conventional Commits Format

We follow the **Conventional Commits** specification for consistent, semantic commit messages:

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### Commit Types

| Type | Description | Example |
|------|-------------|---------|
| `feat` | New feature | `feat: add square root calculation` |
| `fix` | Bug fix | `fix: handle division by zero properly` |
| `docs` | Documentation | `docs: add UML class diagram` |
| `style` | Code style/formatting | `style: fix indentation in Calculator.java` |
| `refactor` | Code refactoring | `refactor: extract validation methods` |
| `test` | Adding/updating tests | `test: add parameterized tests for operations` |
| `chore` | Build/tooling changes | `chore: update Maven dependencies` |
| `perf` | Performance improvements | `perf: optimize calculation algorithms` |

### Commit Message Examples

#### Feature Commits
```bash
git commit -m "feat: implement memory store functionality"
git commit -m "feat(gui): add keyboard shortcuts for operations"
git commit -m "feat(cli): implement command-line calculator interface"
```

#### Bug Fix Commits
```bash
git commit -m "fix: prevent division by zero exception"
git commit -m "fix(gui): correct button layout alignment"
git commit -m "fix(memory): handle negative memory values properly"
```

#### Documentation Commits
```bash
git commit -m "docs: add comprehensive README with usage examples"
git commit -m "docs: create UML class diagram documentation"
git commit -m "docs: update algorithm complexity analysis"
```

#### Test Commits
```bash
git commit -m "test: add unit tests for basic arithmetic operations"
git commit -m "test: increase coverage for exception handling"
git commit -m "test: add integration tests for GUI components"
```

#### Breaking Changes
```bash
git commit -m "feat!: change Calculator constructor signature

BREAKING CHANGE: Calculator constructor now requires ValidationStrategy parameter.
This improves input validation but requires updating all Calculator instantiations."
```

### Commit Message Best Practices

1. **Use imperative mood**: "add feature" not "added feature"
2. **Keep first line under 50 characters**
3. **Capitalize first letter of description**
4. **No period at end of description**
5. **Use body to explain what and why, not how**

## Code Review Process

### Pull Request Requirements

#### Pre-Review Checklist
- [ ] All tests pass (`mvn clean test`)
- [ ] Code compiles without warnings
- [ ] Documentation updated
- [ ] Commit messages follow convention
- [ ] Branch is up to date with main

#### Pull Request Template
```markdown
## Description
Brief description of changes and motivation.

## Type of Change
- [ ] Bug fix (non-breaking change fixing an issue)
- [ ] New feature (non-breaking change adding functionality)
- [ ] Breaking change (fix or feature causing existing functionality to change)
- [ ] Documentation update

## How Has This Been Tested?
- [ ] Unit tests added/updated
- [ ] Integration tests pass
- [ ] Manual testing completed

## Screenshots (if applicable)
Include screenshots for GUI changes.

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Code is commented where necessary
- [ ] Documentation reflects changes
- [ ] No merge conflicts
```

### Review Criteria

#### Code Quality Standards
1. **SOLID Principles**: Single Responsibility, Open/Closed, etc.
2. **Clean Code**: Meaningful names, small functions, clear logic
3. **Error Handling**: Proper exception handling and validation
4. **Test Coverage**: Adequate unit and integration tests
5. **Documentation**: Code comments and external documentation

#### Review Process Steps
1. **Automated Checks**: CI/CD pipeline runs tests and quality checks
2. **Peer Review**: At least one team member reviews code
3. **Discussion**: Address feedback and make necessary changes
4. **Approval**: Reviewer approves changes
5. **Squash-Merge**: Merge feature into main with clean commit

### Review Guidelines for Reviewers

#### What to Look For
- **Functionality**: Does code work as intended?
- **Design**: Is architecture sound and maintainable?
- **Complexity**: Is code unnecessarily complex?
- **Tests**: Are tests comprehensive and meaningful?
- **Naming**: Are variables and methods well-named?
- **Comments**: Is complex logic properly explained?

#### Providing Feedback
```markdown
# Good feedback examples:

## Suggestion
Consider extracting this validation logic into a separate method for reusability.

## Question
Why did you choose ArrayList over LinkedList here? Performance implications?

## Praise
Excellent error handling! The custom exceptions make debugging much easier.

## Required Change
This method violates SRP by handling both validation and calculation. Please split.
```

## Development Environment Setup

### Prerequisites

#### Required Software
- **Java 22 or higher**: Latest LTS version
- **Maven 3.8+**: Build and dependency management
- **Git 2.30+**: Version control
- **IDE**: VS Code, IntelliJ IDEA, or Eclipse

#### Installation Verification
```bash
# Verify Java installation
java -version
# Expected: openjdk version "22.0.1" or higher

# Verify Maven installation
mvn -version
# Expected: Apache Maven 3.8.x or higher

# Verify Git installation
git --version
# Expected: git version 2.30.x or higher
```

### Project Setup

#### Initial Clone and Setup
```bash
# Clone repository
git clone https://github.com/username/Calculator-1.git
cd Calculator-1

# Verify project structure
ls -la
# Expected: src/, docs/, pom.xml, README.md, etc.

# Install dependencies and compile
mvn clean compile

# Run tests to verify setup
mvn test

# Run application to verify functionality
mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorGUI"
```

#### IDE Configuration

**VS Code Setup:**
```json
// .vscode/settings.json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.test.defaultConfig": "default",
    "java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml"
}
```

**IntelliJ IDEA Setup:**
1. Import as Maven project
2. Set Project SDK to Java 22
3. Enable annotation processing
4. Configure code style to Google Java Style

### Development Tools

#### Recommended VS Code Extensions
- **Extension Pack for Java**: Complete Java development
- **Maven for Java**: Maven integration
- **Test Runner for Java**: JUnit test execution
- **GitLens**: Enhanced Git capabilities
- **SonarLint**: Code quality analysis

#### Maven Plugins Configuration
```xml
<!-- Already configured in pom.xml -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0</version>
</plugin>
```

## Testing Strategy

### Testing Pyramid

```
    /\
   /  \     E2E Tests (Few)
  /____\    Integration Tests (Some)
 /      \   Unit Tests (Many)
/__________\
```

### Unit Testing Approach

#### Test Categories
1. **Basic Operations**: Arithmetic calculations
2. **Advanced Functions**: Power, roots, factorial
3. **Memory Operations**: Store, recall, clear
4. **Error Handling**: Exception scenarios
5. **Edge Cases**: Boundary conditions

#### Testing Best Practices
```java
@DisplayName("Calculator Basic Operations")
class CalculatorBasicOperationsTest {
    
    private Calculator calculator;
    private static final double DELTA = 0.0001;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @ParameterizedTest
    @DisplayName("Addition should work with various inputs")
    @CsvSource({
        "10.0, 5.0, 15.0",
        "0.0, 0.0, 0.0",
        "-5.0, 3.0, -2.0",
        "1.5, 2.5, 4.0"
    })
    void testAddition(double a, double b, double expected) throws CalculatorException {
        assertEquals(expected, calculator.add(a, b), DELTA);
    }
    
    @Test
    @DisplayName("Division by zero should throw appropriate exception")
    void testDivisionByZero() {
        DivisionByZeroException exception = assertThrows(
            DivisionByZeroException.class,
            () -> calculator.divide(10.0, 0.0)
        );
        assertTrue(exception.getMessage().contains("Cannot divide"));
    }
}
```

### Test Execution Commands
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CalculatorTest

# Run tests with coverage report
mvn test jacoco:report

# Run tests in specific package
mvn test -Dtest="com.calculator.*Test"
```

### Integration Testing

#### GUI Integration Tests
```java
@Test
@DisplayName("GUI should handle button clicks correctly")
void testGUIButtonInteraction() {
    // Simulate button clicks
    // Verify display updates
    // Check calculation results
}
```

## Deployment Process

### Build Process

#### Maven Build Lifecycle
```bash
# Clean previous builds
mvn clean

# Compile source code
mvn compile

# Run tests
mvn test

# Package application
mvn package

# Install to local repository
mvn install
```

#### Build Verification
```bash
# Verify JAR creation
ls -la target/
# Expected: calculator-1.0.0.jar

# Test JAR execution
java -jar target/calculator-1.0.0.jar
```

### Release Process

#### Version Management
```bash
# Update version in pom.xml
<version>1.1.0</version>

# Create release tag
git tag -a v1.1.0 -m "Release version 1.1.0"
git push origin v1.1.0
```

#### Release Checklist
- [ ] All tests pass
- [ ] Documentation updated
- [ ] Version number incremented
- [ ] Release notes prepared
- [ ] Tag created and pushed

## Contributing Guidelines

### Getting Started

#### First-Time Contributors
1. **Fork the repository** on GitHub
2. **Clone your fork** locally
3. **Set up development environment** (see above)
4. **Create feature branch** for your contribution
5. **Make changes** following coding standards
6. **Add tests** for new functionality
7. **Update documentation** as needed
8. **Submit pull request** with clear description

### Contribution Process

#### Step-by-Step Guide
```bash
# 1. Fork and clone
git clone https://github.com/yourusername/Calculator-1.git
cd Calculator-1

# 2. Add upstream remote
git remote add upstream https://github.com/original/Calculator-1.git

# 3. Create feature branch
git checkout -b feature/your-feature-name

# 4. Make changes and commit
git add .
git commit -m "feat: add your feature description"

# 5. Push to your fork
git push origin feature/your-feature-name

# 6. Create pull request via GitHub
```

### Code Standards

#### Java Coding Conventions
- **Naming**: Use camelCase for variables and methods
- **Classes**: Use PascalCase for class names
- **Constants**: Use UPPER_SNAKE_CASE
- **Indentation**: 4 spaces (no tabs)
- **Line Length**: Maximum 120 characters
- **Braces**: Opening brace on same line

#### Documentation Requirements
- **Public methods**: Must have Javadoc comments
- **Complex logic**: Inline comments explaining approach
- **Classes**: Class-level documentation with purpose
- **README updates**: For new features or changes

### Quality Gates

#### Before Submitting PR
1. **Code compiles** without warnings
2. **All tests pass** (`mvn test`)
3. **Code follows** style guidelines
4. **Documentation** is updated
5. **Commit messages** follow convention
6. **No merge conflicts** with main branch

#### Automated Checks
- **Build verification**: Maven compile and test
- **Code quality**: Static analysis tools
- **Test coverage**: Minimum coverage thresholds
- **Security scan**: Dependency vulnerability check

---

## Summary

This development workflow ensures:

✅ **Clean, deployable main branch** at all times  
✅ **Professional Git practices** with squash-merge  
✅ **Consistent code quality** through reviews  
✅ **Comprehensive testing** strategy  
✅ **Clear contribution** guidelines  
✅ **Academic standards** demonstration  

Following these practices demonstrates understanding of professional software development methodologies and prepares the codebase for academic evaluation and potential industry use.